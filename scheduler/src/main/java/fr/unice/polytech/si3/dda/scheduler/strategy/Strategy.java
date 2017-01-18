package fr.unice.polytech.si3.dda.scheduler.strategy;


import java.util.List;

import fr.unice.polytech.si3.dda.exception.GlobalException;
import fr.unice.polytech.si3.dda.instruction.Instruction;
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
public interface Strategy {

	/**
	 * Gets instructions.
	 *
	 * @return instructions
	 */
	public void calculateInstructions() throws GlobalException;
	
	/**
	 * Accessor of instructionsLists
	 * @return The instructions list calculated with the last context
	 */
	public List<Instruction> getInstructions();
}
