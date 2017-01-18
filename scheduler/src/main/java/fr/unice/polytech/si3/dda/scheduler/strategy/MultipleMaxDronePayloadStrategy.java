package fr.unice.polytech.si3.dda.scheduler.strategy;

import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Drone;
import fr.unice.polytech.si3.dda.common.Fleet;
import fr.unice.polytech.si3.dda.exception.GlobalException;
import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.instruction.DeliverInstruction;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;

import java.util.*;

/**
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class MultipleMaxDronePayloadStrategy extends Strategy {

	/**
	 * Normal constructor of MultipleMaxDronePayloadStrategy
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
        Mapping mapping = context.getMap();

        for (int i = 0; i < context.getMaxDrones(); i++) {
            Drone drone = fleet.getDrone(i);
            Warehouse closestWarehouse = mapping.getWarehouse(drone.getCoordinates());
            for (Map.Entry<Coordinates, Warehouse> entry : mapping.getWarehouses().entrySet()) {
                if (!entry.getValue().isEmpty()) {
                    if (closestWarehouse.distance(drone) > entry.getValue().distance(drone))
                        closestWarehouse = entry.getValue();
                    break;
                }
            }
            if (closestWarehouse == null) {
                throw new ProductNotFoundException();
            }
            System.out.println("\n\n *********************************");
            loadDrone(orders, i, closestWarehouse);
            System.out.println("\n\n *********************************");

            DeliveryPoint closestDeliveryPoint = null;
            Map<Coordinates, DeliveryPoint> deliveryPoints = context.getMap().getDeliveryPoints();
            for (Product product : drone.getLoadedProducts()) {

                System.out.println("++++++++++++++++++++++");
                System.out.println(product);
                for (Map.Entry<Coordinates, DeliveryPoint> deliveryPoint : deliveryPoints.entrySet()) {
                    if (deliveryPoint.getValue().getOrder().getRemaining().size() == 0) {
                        continue;
                    }
                    System.out.println("####################################");
                    System.out.println(deliveryPoint.getValue().getOrder().getRemaining());
                    if (closestDeliveryPoint == null || (deliveryPoint.getValue().getOrder().getRemaining().contains(product))) {
                        closestDeliveryPoint = deliveryPoint.getValue();
                    }
                }
                drone.move(closestDeliveryPoint.getCoordinates());

                int count = 0;
                System.out.println("-------------------");
                System.out.println("drone : " + drone.getLoadedProducts());
                System.out.println("order : " + closestDeliveryPoint.getOrder().getRemaining());
                for (Product other : closestDeliveryPoint.getOrder().getRemaining()) {
                    if (drone.getNumberOf(product) == 0) continue;
                    System.out.println(product.equals(other));
                    if (product.equals(other)) {
                        count++;
                        drone.unload(product);
                        closestDeliveryPoint.deliver(product);
                        closestDeliveryPoint.removeThisProduct(product);
                    }
                }
                if (count > 0) {
                    System.out.println("DELIVER");
                    instructionsLists.add(new DeliverInstruction(i, closestDeliveryPoint.getId(), product.getId(), count));
                }
            }
            // TODO: 18/01/2017 Si maxdrone atteind mais pas tout les order ok restart
        }
        System.out.println(instructionsLists);
    }

    /**
     * 
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
     * 
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
                Map<Product, Integer> tempoProducts = tempoOrders.get(i).getProducts();;
                for (Map.Entry<Product, Integer> entryOrder : tempoProducts.entrySet()) {
                    if (entry.getValue() > 0
                            && entryOrder.getValue() > 0
                            && entry.getKey().getWeight() <= drone.getMaxPayload() - drone.getUsedPayload()) {
                        drone.load(entry.getKey());
                        warehouse.pullOutProduct(entry.getKey());
                        orderedStock.put(entry.getKey(), orderedStock.get(entry.getKey()) - 1);
                        tempoOrders.get(i).removeThisProduct(entryOrder.getKey());
                    }

                }
            }
        }

        addLoadInstructions(warehouse, indexDrone);
    }

}
