package fr.unice.polytech.si3.dda.instruction;

/**
 * Class LoadInstruction
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class LoadInstruction implements IInstruction {
	int droneNumber;
	int idWarehouse;
	int productType;
	int numberOfProducts;

	public LoadInstruction(int droneNumber, int idWarehouse, int productType, int numberOfProducts) {
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
		return droneNumber + " " + "L" + " " + idWarehouse + " " + productType + " " + numberOfProducts;
	}
}
