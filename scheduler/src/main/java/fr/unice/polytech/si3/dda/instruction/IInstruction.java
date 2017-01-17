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
public interface IInstruction {
	
	int execute(Context ctx) throws ProductNotFoundException, WrongIdException, OverLoadException;
	
}
