package fr.unice.polytech.si3.dda.instruction;


import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Drone;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.order.Product;

/**
 * Class DeliverInstruction
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class DeliverInstruction extends Instruction {
	int orderNumber;

	/**
	 * Instantiates a new deliver fr.unice.polytech.si3.dda.exception.instruction.
	 *
	 * @param droneNumber the drone number
	 * @param orderNumber the fr.unice.polytech.si3.dda.exception.order number
	 * @param productType the product type
	 * @param numberOfProducts the number of products
	 */
	public DeliverInstruction(int droneNumber, int orderNumber, int productType, int numberOfProducts) {
		this.droneNumber = droneNumber;
		this.orderNumber = orderNumber;
		this.productType = productType;
		this.numberOfProducts = numberOfProducts;
	}

	/**
	 * @return the orderNumber
	 */
	public int getOrderNumber() {
		return orderNumber;
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
		int cost = cost(ctx);
		d.move(dp.getCoordinates());
		return cost;
	}
	
	@Override
	public int cost(Context ctx) throws WrongIdException {
		Drone d = ctx.getFleet().getDrone(droneNumber);
		DeliveryPoint dp = ctx.getMap().getDeliveryPoint(orderNumber);
		int distance = (int) Math.ceil(d.getCoordinates().distance(dp.getCoordinates()));
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
		if (this == o) 
			return true;
		if (!(o instanceof DeliverInstruction)) 
			return false;

		DeliverInstruction that = (DeliverInstruction) o;

		if (droneNumber != that.droneNumber) 
			return false;
		if (orderNumber != that.orderNumber) 
			return false;
		if (productType != that.productType) 
			return false;
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
