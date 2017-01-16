package fr.unice.polytech.si3.dda.scheduler.strategy;

import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.scheduler.Fleet;

import java.util.List;

import fr.unice.polytech.si3.dda.instruction.IInstruction;

/**
 * 
 * Class SingleDroneStrategy
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 *
 */
public class SingleDroneStrategy {
	private Context context;
	private Fleet fleet;
	
	/**
	 * Normal constructor of SingleDroneStrategy
	 * @param context Context of execution
	 * @param fleet Fleet of drones used for this strategy
	 */
	public SingleDroneStrategy(Context context, Fleet fleet){
		this.context = context;
		this.fleet = fleet;
	}
	
	/**
	 * Return the list of all instructions of the strategy
	 * @return list of all instructions of the strategy
	 */
	public List<IInstruction> getInstructions(){
		
	}
	
}
