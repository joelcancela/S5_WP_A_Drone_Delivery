package fr.unice.polytech.si3.dda.scheduler.strategy;

import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.instruction.DeliverInstruction;
import fr.unice.polytech.si3.dda.instruction.IInstruction;
import fr.unice.polytech.si3.dda.instruction.LoadInstruction;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.scheduler.Fleet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The MultipleDroneStrategy.
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class MultipleDroneStrategy implements Strategy {
    private Fleet fleet;
    private Mapping mapping;
    private Context context;
    private List<IInstruction> instructionList;
    private List<Warehouse> visitedWarehouse;

    /**
     * Constructs a default MultipleDroneStrategy.
     * This strategy use a drone for one product.
     *
     * @param context the context.
     */
    public MultipleDroneStrategy(Context context) {
        this.context = context;
        this.mapping = context.getMap();
        this.fleet = context.getFleet();
        instructionList = new ArrayList<>();
        visitedWarehouse = new ArrayList<>();
    }

    /**
     * Return the list of instruction representing the strategy.
     *
     * @return the strategy.
     * @throws Exception
     */
    @Override
    public List<IInstruction> getInstructions() throws Exception {
        int numberOfDrones = fleet.getDronesNumber();
        int numberOfOrder = mapping.getOrders().size();

        int droneIndex = 0;
        //For all order
        for (int i = 0; i < numberOfOrder; i++) {
            DeliveryPoint deliveryPoint = mapping.getDeliveryPoint(i);
            Map<Product, Integer> products = deliveryPoint.getOrder().getProducts();
            Set<Product> productSet = products.keySet();
            //For all product
            for (Product product : productSet) {
                //Take one drone
                droneIndex++;
                Warehouse warehouse = mapping.getWarehouse(fleet.getDrone(droneIndex).getCoordinates());
                //If the current warehouse contain the product
                visitedWarehouse = new ArrayList<>();
                if (!fleet.getDrone(droneIndex).isEmpty()) {
                    loadFromWarehouse(droneIndex, product, warehouse);
                    deliverProduct(droneIndex, product, deliveryPoint);
                }

                if (droneIndex == numberOfDrones - 1) droneIndex = 0;
            }
        }


        return instructionList;

    }

    /**
     * Deliver move the drone and deliver the product.
     *
     * @param droneIndex    the index of the drone carrying the product.
     * @param product       the product delivered.
     * @param deliveryPoint the delivery point.
     */
    public void deliverProduct(int droneIndex, Product product, DeliveryPoint deliveryPoint) throws ProductNotFoundException {
        instructionList.add(new DeliverInstruction(droneIndex, deliveryPoint.getId(), product.getId(), 1));
        fleet.getDrone(droneIndex).move(deliveryPoint.getCoordinates());
        fleet.getDrone(droneIndex).unload(product);
        deliveryPoint.deliver(product);
    }

    /**
     * Try to load an item from a warehouse.
     *
     * @param droneId   the drone which load the item.
     * @param product   the product to load.
     * @param warehouse the warehouse where to laod.
     * @throws Exception
     */
    public void loadFromWarehouse(int droneId, Product product, Warehouse warehouse) throws Exception {
        if (warehouse == null)
            searchForItem(droneId, product, warehouse);
        if (warehouse.howManyProduct(product) > 0) {
            fleet.getDrone(droneId).load(product);
            instructionList.add(new LoadInstruction(droneId, warehouse.getId(), product.getId(), 1));
        } else {
            searchForItem(droneId, product, warehouse);
        }
    }

    /**
     * Search for warehouse where we can load an product.
     *
     * @param droneId   the drone which load the product.
     * @param product   the product to load.
     * @param warehouse the warehouse where the drone can't load the product.
     * @throws Exception
     */
    public void searchForItem(int droneId, Product product, Warehouse warehouse) throws Exception {
        visitedWarehouse.add(warehouse);
        int j = 0;
        for (; j < mapping.getWarehouses().size(); j++) {
            Warehouse nextWarehouse = mapping.getWarehouse(j);
            if (!visitedWarehouse.contains(nextWarehouse) &&
                    !nextWarehouse.getCoordinates().equals(fleet.getDrone(droneId).getCoordinates())) {
                fleet.getDrone(droneId).move(nextWarehouse.getCoordinates());
                loadFromWarehouse(droneId, product, nextWarehouse);
                return;
            }
        }
        if (j == mapping.getWarehouses().size())
            throw new ProductNotFoundException();
    }

}