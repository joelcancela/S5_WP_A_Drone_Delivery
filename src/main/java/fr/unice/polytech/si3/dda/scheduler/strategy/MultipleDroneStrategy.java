package fr.unice.polytech.si3.dda.scheduler.strategy;

import fr.unice.polytech.si3.dda.instruction.IInstruction;
import fr.unice.polytech.si3.dda.instruction.LoadInstruction;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Drone;
import fr.unice.polytech.si3.dda.scheduler.Fleet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by alexh on 16/01/2017.
 */
public class MultipleDroneStrategy implements Strategy {
    private Fleet fleet;
    private Mapping mapping;

    public MultipleDroneStrategy(Fleet fleet, Mapping mapping) {
        this.fleet = fleet;
        this.mapping = mapping;
    }

    @Override
    public List<IInstruction> getInstructions() {
        List<IInstruction> instructionList = new ArrayList<>();
        int numberOfDrones = fleet.getDronesNumber();
        int numberOfOrder = mapping.getOrders().size();

        if (numberOfDrones > numberOfOrder) {
            for (int i = 0; i < numberOfOrder; i++){
                Drone drone = fleet.getDrone(i);
                Order order = mapping.getOrders().get(i);
                Map<Product, Integer> products = order.getProducts();

                Warehouse warehouse = mapping.getWarehouses().get(0);
                Set<Product> neededProducts = products.keySet();
                for (Product product : neededProducts) {
                        warehouse.pullOutProduct(product);
//                    instructionList.add(new LoadInstruction(i, 0, product.));
                }


            }
        }
        return instructionList;

    }


}
