package fr.unice.polytech.si3.dda.instruction;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.scheduler.Context;

/**
 * Interface Instruction
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public abstract class Instruction {

	protected int droneNumber;
	protected int idWarehouse;
	protected int productType;
	protected int numberOfProducts;

	/**
	 * Execute an instruction
	 * @param ctx Context used
	 * @return Cost of instruction
	 * @throws ProductNotFoundException
	 * @throws WrongIdException
	 * @throws OverLoadException
	 */
	public abstract int execute(Context ctx) throws ProductNotFoundException, WrongIdException, OverLoadException;

	public int getDroneNumber() {
		return droneNumber;
	}
}