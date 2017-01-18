package fr.unice.polytech.si3.dda.mapping;

import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;

/**
 * Class DeliveryPoint
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class DeliveryPoint extends PointOfInterest {
	private Order order;

	/**
	 * Default constructor of DeliveryPoint
	 */
	public DeliveryPoint() {
		this(null, 0);
	}

	/**
	 * Normal constructor of DeliveryPoint
	 *
	 * @param order Order of the DeliveryPoint
	 * @param id    Id of the DeliveryPoint
	 * @throws IllegalArgumentException if the fr.unice.polytech.si3.dda.exception.order is null
	 */
	public DeliveryPoint(Order order, int id) {
		this(order, null, id);
	}

	/**
	 * Normal constructor of DeliveryPoint
	 *
	 * @param order       Order of the DeliveryPoint
	 * @param coordinates Coordinates of the deliveryPoint
	 * @param id          Id of the DeliveryPoint
	 * @throws IllegalArgumentException if the fr.unice.polytech.si3.dda.exception.order is null
	 */
	public DeliveryPoint(Order order, Coordinates coordinates, int id) {
		super(coordinates, id);

		if (order == null)
			throw new IllegalArgumentException("Argument passed \"fr.unice.polytech.si3.dda.exception.order\" is null.");
		else
			this.order = order;
	}

	public DeliveryPoint(DeliveryPoint dp) {
		this(dp.order.copy(), dp.coordinates, dp.id);
	}

	/**
	 * Getter of fr.unice.polytech.si3.dda.exception.order
	 *
	 * @return Order of the DeliveryPoint
	 */
	public Order getOrder() {
		return order;
	}

	@Override
	/**
	 * Inherited method
	 */
	public boolean isWarehouse() {
		return false;
	}

	@Override
	/**
	 * Inherited method
	 */
	public boolean isDeliveryPoint() {
		return true;
	}

	@Override
	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return id + " @(" + coordinates.getX() + "," + coordinates.getY() + ") : " + order.toString();
	}

	/**
	 * Remove a product to the list of remaining products
	 *
	 * @param product Product to remove
	 */
	public void deliver(Product product) {
		order.deliver(product);
	}

	/**
	 * Remove a product to the map of the fr.unice.polytech.si3.dda.exception.order
	 *
	 * @param product Product to remove
	 */
	public void removeThisProduct(Product product) {
		order.removeThisProduct(product);
	}

	public DeliveryPoint copy() {
		return new DeliveryPoint(this);
	}

}
