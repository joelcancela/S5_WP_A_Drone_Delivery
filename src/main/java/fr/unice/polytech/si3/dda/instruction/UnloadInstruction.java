package fr.unice.polytech.si3.dda.instruction;

/**
 * Class UnloadInstruction
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class UnloadInstruction implements IInstruction {
	int droneNumber;
	int idWarehouse;
	int productType;
	int numberOfProducts;

	public UnloadInstruction(int droneNumber, int idWarehouse, int productType, int numberOfProducts) {
		this.droneNumber = droneNumber;
		this.idWarehouse = idWarehouse;
		this.productType = productType;
		this.numberOfProducts = numberOfProducts;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return droneNumber + " " + "U" + " " + idWarehouse + " " + productType + " " + numberOfProducts;
	}
}
