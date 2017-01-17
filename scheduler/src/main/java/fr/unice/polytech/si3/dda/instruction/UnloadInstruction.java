package fr.unice.polytech.si3.dda.instruction;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.scheduler.Drone;

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

	/**
	 * Instantiates a new unload instruction.
	 *
	 * @param droneNumber the drone number
	 * @param idWarehouse the id warehouse
	 * @param productType the product type
	 * @param numberOfProducts the number of products
	 */
	public UnloadInstruction(int droneNumber, int idWarehouse, int productType, int numberOfProducts) {
		this.droneNumber = droneNumber;
		this.idWarehouse = idWarehouse;
		this.productType = productType;
		this.numberOfProducts = numberOfProducts;
	}

	@Override
	public int execute(Context ctx) throws ProductNotFoundException, WrongIdException, OverLoadException {
		Drone d = ctx.getFleet().getDrone(droneNumber);
		Warehouse w = ctx.getMap().getWarehouse(idWarehouse);
		for (int i=0; i<numberOfProducts; i++) {
			Product p = ctx.getProducts().get(productType);
			w.addProduct(p, 1);
			d.unload(p);
		}
		int distance = (int) Math.ceil(d.getCoordinates().distance(w.getCoordinates()));
		d.move(w.getCoordinates());
		return distance+1;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UnloadInstruction)) return false;

		UnloadInstruction that = (UnloadInstruction) o;

		if (droneNumber != that.droneNumber) return false;
		if (idWarehouse != that.idWarehouse) return false;
		if (productType != that.productType) return false;
		return numberOfProducts == that.numberOfProducts;
	}

	@Override
	public int hashCode() {
		int result = droneNumber;
		result = 31 * result + idWarehouse;
		result = 31 * result + productType;
		result = 31 * result + numberOfProducts;
		return result;
	}
}
