package fr.unice.polytech.si3.dda.instruction;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.scheduler.Drone;

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

	/**
	 * Instantiates a new load instruction.
	 *
	 * @param droneNumber the drone number
	 * @param idWarehouse the id warehouse
	 * @param productType the product type
	 * @param numberOfProducts the number of products
	 */
	public LoadInstruction(int droneNumber, int idWarehouse, int productType, int numberOfProducts) {
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
			w.pullOutProduct(p);
			d.load(p);
		}
		int distance = (int) Math.ceil(d.getCoordinates().distance(w.getCoordinates()));
		d.move(w.getCoordinates());
		return distance+2;
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
