package fr.unice.polytech.si3.dda.scheduler.strategy;


import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Fleet;
import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.instruction.DeliverInstruction;
import fr.unice.polytech.si3.dda.instruction.LoadInstruction;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * The MultipleDroneStrategy.
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class MultipleDroneStrategy extends Strategy {
    private Fleet fleet;
    private Mapping mapping;
    private List<Warehouse> visitedWarehouse;

    /**
     * Constructs a default MultipleDroneStrategy.
     * This strategy use a drone for one product.
     *
     * @param context the context.
     */
    public MultipleDroneStrategy(Context context) {
        this.mapping = context.getMap();
        this.fleet = context.getFleet();
        instructionsLists = new ArrayList<>();
        visitedWarehouse = new ArrayList<>();
    }

    /**
     * Return the list of fr.unice.polytech.si3.dda.exception.instruction representing the strategy.
     *
     * @return the strategy.
     * @throws WrongIdException
     * @throws ProductNotFoundException
     * @throws OverLoadException
     * @throws Exception
     */
    @Override
    public void calculateInstructions() throws WrongIdException, OverLoadException, ProductNotFoundException {
        int numberOfDrones = fleet.getDronesNumber();
        int numberOfOrder = mapping.getOrders().size();

        int droneIndex = 0;
        //For all fr.unice.polytech.si3.dda.exception.order
        for (int i = 0; i < numberOfOrder; i++) {
            DeliveryPoint deliveryPoint = mapping.getDeliveryPoint(i);
            List<Product> products = deliveryPoint.getOrder().getRemaining();
            //For all product
            while (!products.isEmpty()) {
                visitedWarehouse = new ArrayList<>();
                if (fleet.getDrone(droneIndex).isEmpty()) {
                    loadFromWarehouse(droneIndex, products.get(0), mapping.getWarehouse(0));
                    deliverProduct(droneIndex, products.get(0), deliveryPoint);
                    products = deliveryPoint.getOrder().getRemaining();
                }
                droneIndex++;

                if (droneIndex == numberOfDrones)
                    droneIndex = 0;
            }
        }

    }

    /**
     * Deliver move the drone and deliver the product.
     *
     * @param droneIndex    the index of the drone carrying the product.
     * @param product       the product delivered.
     * @param deliveryPoint the delivery point.
     */
    public void deliverProduct(int droneIndex, Product product, DeliveryPoint deliveryPoint) throws ProductNotFoundException {
        instructionsLists.add(new DeliverInstruction(droneIndex, deliveryPoint.getId(), product.getId(), 1));
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
     * @throws WrongIdException
     * @throws OverLoadException
     * @throws ProductNotFoundException
     * @throws Exception
     */
    public void loadFromWarehouse(int droneId, Product product, Warehouse warehouse) throws WrongIdException, OverLoadException, ProductNotFoundException {
        if (warehouse == null) {
            searchForItem(droneId, product, warehouse);
            return;
        } else if (warehouse.howManyProduct(product) > 0) {
            fleet.getDrone(droneId).load(product);
            warehouse.pullOutProduct(product);
            instructionsLists.add(new LoadInstruction(droneId, warehouse.getId(), product.getId(), 1));
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
     * @throws WrongIdException
     * @throws ProductNotFoundException
     * @throws OverLoadException
     * @throws Exception
     */
    public void searchForItem(int droneId, Product product, Warehouse warehouse) throws WrongIdException, ProductNotFoundException, OverLoadException {
        visitedWarehouse.add(warehouse);
        int j = 0;
        for (; j < mapping.getWarehouses().size(); j++) {
            Warehouse nextWarehouse = mapping.getWarehouse(j);
            if (warehouse.equals(nextWarehouse)) continue;
            if (!visitedWarehouse.contains(nextWarehouse) &&
                    !nextWarehouse.getCoordinates().equals(fleet.getDrone(droneId).getCoordinates()) &&
                    nextWarehouse.howManyProduct(product) > 0) {
                fleet.getDrone(droneId).move(nextWarehouse.getCoordinates());
                loadFromWarehouse(droneId, product, nextWarehouse);
                return;
            }
        }
        if (j == mapping.getWarehouses().size())
            throw new ProductNotFoundException("Product : " + product.getId());
    }

}