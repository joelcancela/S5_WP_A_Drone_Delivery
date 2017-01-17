package fr.unice.polytech.si3.dda.instruction;

import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.scheduler.Drone;

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

	@Override
	public int execute(Context ctx) throws ProductNotFoundException, WrongIdException {
		Drone d = ctx.getFleet().getDrone(droneNumber);
		DeliveryPoint dp = ctx.getMap().getDeliveryPoint(orderNumber);
		for (int i=0; i<numberOfProducts; i++) {
			Product p = ctx.getProducts().get(productType);
			dp.getOrder().deliver(p);
			d.unload(p);
		}
		int distance = (int) Math.ceil(d.getCoordinates().distance(dp.getCoordinates()));
		d.move(dp.getCoordinates());
		return distance+1;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof DeliverInstruction)) return false;

		DeliverInstruction that = (DeliverInstruction) o;

		if (droneNumber != that.droneNumber) return false;
		if (orderNumber != that.orderNumber) return false;
		if (productType != that.productType) return false;
		return numberOfProducts == that.numberOfProducts;
	}

	@Override
	public int hashCode() {
		int result = droneNumber;
		result = 31 * result + orderNumber;
		result = 31 * result + productType;
		result = 31 * result + numberOfProducts;
		return result;
	}
}
