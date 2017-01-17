package fr.unice.polytech.si3.dda.instruction;

import fr.unice.polytech.si3.dda.mapping.Mapping;

/**
 * Class DeliverInstruction
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class DeliverInstruction implements IInstruction {
	int droneNumber;
	int orderNumber;
	int productType;
	int numberOfProducts;

	/**
	 * Instantiates a new deliver instruction.
	 *
	 * @param droneNumber the drone number
	 * @param orderNumber the order number
	 * @param productType the product type
	 * @param numberOfProducts the number of products
	 */
	public DeliverInstruction(int droneNumber, int orderNumber, int productType, int numberOfProducts) {
		this.droneNumber = droneNumber;
		this.orderNumber = orderNumber;
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
		return droneNumber + " " + "D" + " " + orderNumber + " " + productType + " " + numberOfProducts;
	}
}
