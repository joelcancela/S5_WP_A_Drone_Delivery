package fr.unice.polytech.si3.dda.scheduler.strategy;

import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Drone;
import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.util.Coordinates;

import java.util.List;
import java.util.Map;

/**
 * Class SingleDroneStrategyPayload
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class SingleDroneStrategyPayload extends MultipleMaxDronePayloadStrategy {

	/**
	 * Normal constructor of SingleDroneStrategy
	 *
	 * @param context Context of execution
	 */
	public SingleDroneStrategyPayload(Context context) {
		super(context);
	}

	@Override
	public void calculateInstructions() throws OverLoadException, ProductNotFoundException {
		Drone droneUsed = context.getFleet().getDrone(0);

		List<Order> orders = context.getMap().getOrders();
		Warehouse warehouse = context.getFirstWarehouse();

		while (!isOrdersCompleted(orders)) {
			loadDrone(orders, 0, warehouse);
			findPath(0);

			Map<Coordinates, Warehouse> warhouses = context.getMap().getWarehouses();
			warehouse = findTheNextWarehouse(orders, warhouses, droneUsed);
		}
	}

}
