package fr.unice.polytech.si3.dda.scheduler.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Drone;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.mapping.PointOfInterest;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;

/**
 * 
 * Class SingleDroneStrategyPayload
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 *
 */
public class SingleDroneStrategyPayload extends MultipleMaxDronePayloadStrategy {
	
	/**
	 * Normal constructor of SingleDroneStrategy
	 * @param context Context of execution
	 */
	public SingleDroneStrategyPayload(Context context){
		super(context);
	}
	
	@Override
	public void calculateInstructions(){
		Drone droneUsed =  context.getFleet().getDrone(0);
		
		List<Order> orders = context.getMap().getOrders();
		Warehouse warehouse = context.getFirstWarehouse();
		List<Map<Product, Integer>> takens = new ArrayList<>();
		
		while(!isOrdersCompleted(orders)){
			//loadDrone(orders, 0, warehouse);

			//Pair<Map<DeliveryPoint, Map<Product, Integer>>, List<PointOfInterest>> pairOfPath = getPathForThoseProducts(takens, warehouse);
			//generateInstructionsForThisPath(pairOfPath, warehouse, droneUsed);
			

			
			Map<Coordinates, Warehouse> warhouses =  context.getMap().getWarehouses();
			warehouse =  findTheNextWarehouse(orders, warhouses, droneUsed);
		}
	}
	
	public List<PointOfInterest> generatePath(Warehouse warehouse, int droneIndex){
		List<PointOfInterest> path = null;
		Drone drone = context.getFleet().getDrone(droneIndex);
		
		Map<Coordinates, DeliveryPoint> dps = context.getMap().getDeliveryPoints();
		List<Product> listOfProducts =  new ArrayList<Product>(drone.getLoadedProducts());
		
		for(Map.Entry<Coordinates, DeliveryPoint> entry : dps.entrySet()){
			for(int i=0; i<listOfProducts.size(); i++){
				
			}
		}
		
		return path;
	}
}
