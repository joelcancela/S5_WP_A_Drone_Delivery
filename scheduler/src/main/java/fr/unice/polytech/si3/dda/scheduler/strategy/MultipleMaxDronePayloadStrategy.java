package fr.unice.polytech.si3.dda.scheduler.strategy;

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
        Map<Coordinates, Warehouse> warehouses = context.getMap().getWarehouses();
        boolean ordersCompleted = false;

        while (!isOrdersCompleted(orders)) {
            for (int i = 0; i < context.getMaxDrones(); i++) {
                Drone drone = fleet.getDrone(i);
                Warehouse closestWarehouse = findTheNextWarehouse(orders, warehouses, fleet.getDrone(i));
                if (closestWarehouse == null)
                    break;

                loadDrone(orders, i, closestWarehouse);

                findPath(i);


            }

            for (int j = 0; j < orders.size(); j++) {
                ordersCompleted = orders.get(j).isCompleted();
                if (!ordersCompleted) break;
            }

        }
    }

    protected void findPath(int droneId) throws ProductNotFoundException {
        Drone drone = context.getFleet().getDrone(droneId);

        DeliveryPoint closestDeliveryPoint = null;
        Map<Coordinates, DeliveryPoint> deliveryPoints = context.getMap().getDeliveryPoints();

        for (Product product : drone.getLoadedProducts()) {

            for (Map.Entry<Coordinates, DeliveryPoint> deliveryPoint : deliveryPoints.entrySet()) {
                if (deliveryPoint.getValue().getOrder().getRemaining().size() == 0) {
                    continue;
                }
                if (closestDeliveryPoint == null || (deliveryPoint.getValue().getOrder().getRemaining().contains(product))) {
                    closestDeliveryPoint = deliveryPoint.getValue();
                }
            }
            drone.move(closestDeliveryPoint.getCoordinates());

            int count = 0;
            for (Product other : closestDeliveryPoint.getOrder().getRemaining()) {
                if (drone.getNumberOf(product) == 0) continue;
                if (product.equals(other)) {
                    count++;
                    drone.unload(product);
                    closestDeliveryPoint.deliver(product);
                    closestDeliveryPoint.removeThisProduct(product);
                }
            }
            if (count > 0) {
                instructionsLists.add(new DeliverInstruction(droneId, closestDeliveryPoint.getId(), product.getId(), count));
            }
        }
    }

    /**
     * Order a stock by the weight of products
     * @param currentStock Stock to order
     * @return Ordered stock
     */
    private Map<Product, Integer> orderAWharehousStock(Map<Product, Integer> currentStock) {
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
     * Load products to a drone at a warehouse
     * @param orders List of needed products
     * @param indexDrone Index of the drone in the fleet
     * @param warehouse Warehouse where is the current drone
     * @throws OverLoadException
     * @throws ProductNotFoundException
     */
    protected void loadDrone(List<Order> orders, int indexDrone, Warehouse warehouse) throws OverLoadException, ProductNotFoundException {
        Drone drone = context.getFleet().getDrone(indexDrone);

        List<Order> tempoOrders = new ArrayList<>();
        for (Order tempoOrder : orders) {
            tempoOrders.add(new Order(tempoOrder));
        }


        Map<Product, Integer> orderedStock = orderAWharehousStcok(warehouse.getStock());


        Map<Product, Integer> orderedStock = orderAWharehousStock(warehouse.getStock());

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
                        tempoOrders.get(i).removeThisProduct(entryOrder.getKey());
                    }

                }
            }
        }
        addLoadInstructions(warehouse, indexDrone);
    }

}
