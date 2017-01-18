package fr.unice.polytech.si3.dda.scheduler.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Drone;
import fr.unice.polytech.si3.dda.common.Fleet;
import fr.unice.polytech.si3.dda.exception.GlobalException;
import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.instruction.DeliverInstruction;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;

/**
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class MultipleMaxDronePayloadStrategy extends Strategy {

    /**
     * Normal constructor of MultipleMaxDronePayloadStrategy
     *
     * @param context Context used
     */
    public MultipleMaxDronePayloadStrategy(Context context) {
        this.context = context;
        instructionsLists = new ArrayList<>();
    }

    @Override
    public void calculateInstructions() throws GlobalException {
        List<Order> orders = context.getMap().getOrders();
        Fleet fleet = context.getFleet();
        Map<Coordinates,Warehouse> warehouses = context.getMap().getWarehouses();
        boolean ordersCompleted = false;

        while (!isOrdersCompleted(orders)) {
            for (int i = 0; i < context.getMaxDrones(); i++) {
                System.out.println("IIII : " + i);
                Drone drone = fleet.getDrone(i);
                Warehouse closestWarehouse = findTheNextWarehouse(orders, warehouses, fleet.getDrone(i));
                if (closestWarehouse == null)
                    throw new ProductNotFoundException();
//                System.out.println("\n\n *********************************");
                loadDrone(orders, i, closestWarehouse);
//                System.out.println("\n\n *********************************");

                DeliveryPoint closestDeliveryPoint = null;
                Map<Coordinates, DeliveryPoint> deliveryPoints = context.getMap().getDeliveryPoints();
                for (Product product : drone.getLoadedProducts()) {

//                    System.out.println("++++++++++++++++++++++");
                    System.out.println(product);
                    for (Map.Entry<Coordinates, DeliveryPoint> deliveryPoint : deliveryPoints.entrySet()) {
                        if (deliveryPoint.getValue().getOrder().getRemaining().size() == 0) {
                            continue;
                        }
//                        System.out.println("####################################");
//                        System.out.println(deliveryPoint.getValue().getOrder().getRemaining());
                        if (closestDeliveryPoint == null || (deliveryPoint.getValue().getOrder().getRemaining().contains(product))) {
                            closestDeliveryPoint = deliveryPoint.getValue();
                        }
                    }
                    drone.move(closestDeliveryPoint.getCoordinates());

                    int count = 0;
//                    System.out.println("-------------------");
//                    System.out.println("drone : " + drone.getLoadedProducts());
//                    System.out.println("order : " + closestDeliveryPoint.getOrder().getRemaining());
                    for (Product other : closestDeliveryPoint.getOrder().getRemaining()) {
                        if (drone.getNumberOf(product) == 0) continue;
//                        System.out.println(product.equals(other));
                        if (product.equals(other)) {
                            count++;
                            drone.unload(product);
                            closestDeliveryPoint.deliver(product);
                            closestDeliveryPoint.removeThisProduct(product);
                        }
                    }
                    if (count > 0) {
//                        System.out.println("DELIVER");
                        instructionsLists.add(new DeliverInstruction(i, closestDeliveryPoint.getId(), product.getId(), count));
                    }
                }

            }

            for (int j = 0; j < orders.size(); j++) {
                ordersCompleted = orders.get(j).isCompleted();
                if (!ordersCompleted) break;
            }

            System.out.println(orders);
        }
        System.out.println(instructionsLists);
    }

    /**
     * @param currentStock
     * @return
     */
    private Map<Product, Integer> orderAWharehousStcok(Map<Product, Integer> currentStock) {
        Map<Product, Integer> copy = new HashMap<>(currentStock);
        Map<Product, Integer> orderedStock = new LinkedHashMap<>();
        while (copy.size() > 0) {
            int maxWeight = 0;
            Map.Entry<Product, Integer> tempo = null;
            for (Map.Entry<Product, Integer> entry : copy.entrySet()) {
                if (maxWeight < entry.getKey().getWeight()) {
                    tempo = entry;
                    maxWeight = entry.getKey().getWeight();
                }
            }
            orderedStock.put(tempo.getKey(), tempo.getValue());
            copy.remove(tempo.getKey());
        }

        return orderedStock;
    }

    /**
     * @param orders
     * @param indexDrone
     * @param warehouse
     * @throws OverLoadException
     * @throws ProductNotFoundException
     */
    protected void loadDrone(List<Order> orders, int indexDrone, Warehouse warehouse) throws OverLoadException, ProductNotFoundException {
        Drone drone = context.getFleet().getDrone(indexDrone);
        List<Order> tempoOrders = new ArrayList<>(orders);

        Map<Product, Integer> orderedStock = orderAWharehousStcok(warehouse.getStock());
        for (Map.Entry<Product, Integer> entry : orderedStock.entrySet()) {
            for (int i = 0; i < tempoOrders.size(); i++) {
                Map<Product, Integer> tempoProducts = tempoOrders.get(i).getProducts();
                ;
                for (Map.Entry<Product, Integer> entryOrder : tempoProducts.entrySet()) {
                    if (entry.getValue() > 0
                            && entry.getKey().equals(entryOrder.getKey())
                            && entryOrder.getValue() > 0
                            && entry.getKey().getWeight() <= drone.getMaxPayload() - drone.getUsedPayload()) {
                        drone.load(entry.getKey());
                        warehouse.pullOutProduct(entry.getKey());
                        orderedStock.put(entry.getKey(), orderedStock.get(entry.getKey()) - 1);
                        System.out.println("Je suis : " + i);
                        System.out.println(tempoOrders.get(i));
                        System.out.println("Je veux " + entryOrder.getValue() + " :" + entryOrder.getKey());

                        System.out.println("i try tOU delete this : " + entryOrder.getKey());
                        tempoOrders.get(i).removeThisProduct(entryOrder.getKey());

                        System.out.println("------------------");
                    }

                }
            }
        }
        System.out.println("carried by THIS BITCH :");
        System.out.println(drone.getLoadedProducts());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        addLoadInstructions(warehouse, indexDrone);
//        System.out.println(instructionsLists);
    }

}
