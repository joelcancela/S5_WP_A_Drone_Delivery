package fr.unice.polytech.si3.dda.scheduler.strategy;

import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.scheduler.Drone;
import fr.unice.polytech.si3.dda.scheduler.Fleet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class SingleDroneStrategy implements Strategy{
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
	@Override
	public List<IInstruction> getInstructions(){
		Drone doneUsed = fleet.getDrone(0);
		Map<List<IInstruction>, Integer> instructionsLists = new HashMap<List<IInstruction>, Integer>();
		
		
		
		return null;
	}

	
}
