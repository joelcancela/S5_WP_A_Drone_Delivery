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

	public WaitInstruction(int droneNumber, int turns) {
		this.droneNumber = droneNumber;
		this.turns = turns;
	}

	@Override
	public String toString() {
		return droneNumber + "" + "W" + turns;
	}
}
