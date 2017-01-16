package fr.unice.polytech.si3.dda.instruction;

/**
 * Class WaitInstruction
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class WaitInstruction implements IInstruction {
	int droneNumber;
	int turns;

	/**
	 * WaitInstruction constructor
	 *
	 * @param droneNumber is the number of the drone to apply wait instruction
	 * @param turns       is the number of turns the drone will wait
	 */
	public WaitInstruction(int droneNumber, int turns) {
		this.droneNumber = droneNumber;
		this.turns = turns;
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
}
