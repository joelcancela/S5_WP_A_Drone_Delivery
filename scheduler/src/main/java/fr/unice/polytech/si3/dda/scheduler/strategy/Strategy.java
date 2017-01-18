package fr.unice.polytech.si3.dda.scheduler.strategy;


import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Drone;
import fr.unice.polytech.si3.dda.exception.GlobalException;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.instruction.LoadInstruction;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Product;
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
		throw new UnsupportedOperationException();
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
}
