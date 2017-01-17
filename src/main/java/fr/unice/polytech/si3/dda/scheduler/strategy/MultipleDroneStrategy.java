package fr.unice.polytech.si3.dda.scheduler.strategy;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.instruction.IInstruction;
import fr.unice.polytech.si3.dda.instruction.LoadInstruction;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.scheduler.Drone;
import fr.unice.polytech.si3.dda.scheduler.Fleet;
import fr.unice.polytech.si3.dda.util.Coordinates;

import java.util.*;

/**
 * Created by alexh on 16/01/2017.
 */
public class MultipleDroneStrategy implements Strategy {
    private Fleet fleet;
    private Mapping mapping;
    private Context context;
    private List<IInstruction> instructionList;

    public MultipleDroneStrategy(Fleet fleet, Mapping mapping, Context context) {
        this.fleet = fleet;
        this.mapping = mapping;
        this.context = context;
        instructionList = new ArrayList<>();
    }

    @Override
    public List<IInstruction> getInstructions() throws Exception {
        int numberOfDrones = fleet.getDronesNumber();
        int numberOfOrder = mapping.getOrders().size();

        int droneIndex = 0;
        //For all order
        for (int i = 0; i < numberOfOrder; i++) {
            Order order = mapping.getOrders().get(i);
            Map<Product, Integer> products = order.getProducts();
            Set<Product> productSet = products.keySet();
            //For all product
            for (Product product : productSet) {
                //Take one drone
                for (; droneIndex < numberOfDrones; droneIndex++) {
                    Drone drone = fleet.getDrone(droneIndex);
                    Warehouse warehouse = mapping.getWarehouse(drone.getCoordinates());
                    //If the current warehouse contain the product
                    loadFromWarehouse(droneIndex, product, warehouse);
                    if (warehouse != null && warehouse.howManyProduct(product) <= 0){
                        for (int j = 0; j< mapping.getWarehouses().size(); j++){
                            Warehouse nextWarehouse = mapping.getWarehouse(j);
                            if (warehouse.getId() == nextWarehouse.getId()) continue;

                        }
                    }
                }
            }
        }


        return instructionList;

    }

    public void loadFromWarehouse(int droneId, Product product, Warehouse warehouse) throws OverLoadException {
        if (warehouse != null && warehouse.howManyProduct(product) > 0) {
            fleet.getDrone(droneId).load(product);
            instructionList.add(new LoadInstruction(droneId, warehouse.getId(), product.getId(), 1));
        }
//        else if ()
    }


}
