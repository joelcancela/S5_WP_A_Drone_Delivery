package fr.unice.polytech.si3.dda.instruction;


import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;

/**
 * Class WaitInstruction
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class WaitInstruction extends Instruction {
	int turns;

	/**
	 * WaitInstruction constructor
	 *
	 * @param droneNumber is the number of the drone to apply wait fr.unice.polytech.si3.dda.exception.instruction
	 * @param turns       is the number of turns the drone will wait
	 */
	public WaitInstruction(int droneNumber, int turns) {
		this.droneNumber = droneNumber;
		this.turns = turns;
	}

	@Override
	public int execute(Context ctx) throws ProductNotFoundException, WrongIdException, OverLoadException {
		return turns;
	}
	
	@Override
	public int cost(Context ctx) throws WrongIdException {
		return turns;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return droneNumber + " " + "W" + " " + turns;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) 
			return true;
		if (!(o instanceof WaitInstruction)) 
			return false;

		WaitInstruction that = (WaitInstruction) o;

		if (droneNumber != that.droneNumber) 
			return false;
		return turns == that.turns;
	}

	@Override
	public int hashCode() {
		int result = droneNumber;
		result = 31 * result + turns;
		return result;
	}
}
