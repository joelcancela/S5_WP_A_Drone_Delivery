package fr.unice.polytech.si3.dda.scheduler.strategy;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Drone;
import fr.unice.polytech.si3.dda.exception.GlobalException;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.instruction.LoadInstruction;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;
/**
 * 
 * Interface Strategy
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 *
 */
public class Strategy {
    protected Context context;
    protected List<Instruction> instructionsLists;

	/**
	 * Gets instructions.
	 *
	 * @return instructions
	 */
	public void calculateInstructions() throws GlobalException{
		throw new UnsupportedOperationException();
	};
	
	/**
	 * Accessor of instructionsLists
	 * @return The instructions list calculated with the last context
	 */
	public List<Instruction> getInstructions(){
		return instructionsLists;
	};
	
	/**
	 * Add a load instruction to the strategy list's instructions
	 * @param warehouse Warehouse where the drone is
	 * @param indexDrone Drone index to load
	 */
    protected void addLoadInstructions(Warehouse warehouse, int indexDrone){
    	Drone drone = context.getFleet().getDrone(indexDrone);
		List<Product> alreadyCounted = new ArrayList<>();
		
		for(Product productTemp : drone.getLoadedProducts()){
			if(alreadyCounted.contains(productTemp)){
				continue;
			}
			
			int nbof = 0;
			
			for(int i=0; i<drone.getLoadedProducts().size(); i++)
				if(drone.getLoadedProducts().get(i).equals(productTemp))
					nbof++;
			
			instructionsLists.add(new LoadInstruction(indexDrone, warehouse.getId(), productTemp.getId(), nbof));
			alreadyCounted.add(productTemp);
		}
    }
    
	/**
	 * Check if all orders are done
	 * @param orders List of orders to do
	 * @return true if all orders are done, false otherwise
	 */
	protected boolean isOrdersCompleted(List<Order> orders){
		for(int i=0; i<orders.size(); i++){
			for(Map.Entry<Product, Integer> entry : orders.get(i).getProducts().entrySet()){
				if(entry.getValue()>0)
					return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Find the next warehouse to go (nearest with useful products)
	 * @param orders List of orders to do
	 * @param warhouses List of warehouses
	 * @param droneUsed Current drone
	 * @return The next warehouse chosen by the strategy 
	 */
	protected Warehouse findTheNextWarehouse(List<Order> orders, Map<Coordinates, Warehouse> warhouses, Drone droneUsed){
		double distance = Double.MAX_VALUE;
		Warehouse nextWareHouse =  null;
		for(Map.Entry<Coordinates, Warehouse> entry : warhouses.entrySet()){
			for(int i=0; i<orders.size(); i++){
				for(Map.Entry<Product, Integer> prodcuts: orders.get(i).getProducts().entrySet()){
					double currentDistance = droneUsed.getCoordinates().distance(entry.getKey());
					if(prodcuts.getValue()>0
							&& entry.getValue().howManyProduct(prodcuts.getKey()) > 0
							&& currentDistance<distance){
						nextWareHouse = entry.getValue();
						distance = currentDistance;
					}
				}
			}
		}
		
		return nextWareHouse;
	}
}
