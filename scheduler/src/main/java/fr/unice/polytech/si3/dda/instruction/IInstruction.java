package fr.unice.polytech.si3.dda.instruction;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.scheduler.Context;

/**
 * Interface IInstruction
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
@FunctionalInterface
public interface IInstruction {
	
	/**
	 * Execute an instruction
	 * @param ctx Context used
	 * @return Cost of instruction
	 * @throws ProductNotFoundException
	 * @throws WrongIdException
	 * @throws OverLoadException
	 */
	int execute(Context ctx) throws ProductNotFoundException, WrongIdException, OverLoadException;
	
}
