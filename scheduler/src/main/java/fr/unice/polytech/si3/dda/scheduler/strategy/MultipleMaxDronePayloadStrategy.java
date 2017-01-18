package fr.unice.polytech.si3.dda.scheduler.strategy;

import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Drone;
import fr.unice.polytech.si3.dda.common.Fleet;
import fr.unice.polytech.si3.dda.exception.GlobalException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;

import java.util.List;
import java.util.Map;

/**
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class MultipleMaxDronePayloadStrategy implements Strategy {
    private Context context;


    public MultipleMaxDronePayloadStrategy(Context context) {
        this.context = context;
    }

    @Override
    public void calculateInstructions() throws GlobalException {
        Map<Coordinates, DeliveryPoint> deliveryPoints = context.getMap().getDeliveryPoints();

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

            loadDrone(drone, closestWarehouse);

            DeliveryPoint closestDeliveryPoint = mapping.getDeliveryPoint(0);

            for (Product product : drone.getLoadedProducts()) {
                for (Map.Entry<Coordinates, DeliveryPoint> deliveryPoint : deliveryPoints.entrySet()) {
                    if (deliveryPoint.getValue().getOrder().getRemaining().contains(product)
                            && closestDeliveryPoint.distance(drone)> deliveryPoint.getValue().distance(drone)) {
                        closestDeliveryPoint = deliveryPoint.getValue();
                    }
                }
                drone.move(closestDeliveryPoint.getCoordinates());
                drone.unload(product);
                closestDeliveryPoint.deliver(product);
            }


        }
    }

    @Override
    public List<Instruction> getInstructions() {
        return null;
    }


    public void loadDrone(Drone drone, Warehouse warehouse) {

    }


}
