package fr.unice.polytech.si3.dda.scheduler.strategy;

import java.util.List;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.instruction.IInstruction;

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
	public List<IInstruction> getInstructions() throws WrongIdException, OverLoadException, ProductNotFoundException;

}
