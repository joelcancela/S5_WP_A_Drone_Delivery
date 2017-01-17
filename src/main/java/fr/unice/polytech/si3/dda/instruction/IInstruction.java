package fr.unice.polytech.si3.dda.instruction;

import java.util.List;

import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.order.Product;
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
	
	public void execute();

	int execute(Context ctx) throws ProductNotFoundException;
	
}
