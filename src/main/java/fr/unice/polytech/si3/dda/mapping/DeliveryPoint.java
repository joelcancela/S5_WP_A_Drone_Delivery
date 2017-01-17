package fr.unice.polytech.si3.dda.mapping;

import fr.unice.polytech.si3.dda.order.Order;
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
        this(null);
    }

    /**
     * Normal constructor of DeliveryPoint
     *
     * @param order Order of the DeliveryPoint
     * @throws IllegalArgumentException if the order is null
     */
    public DeliveryPoint(Order order) {
        this(order, null);
    }

	/**
	 * Normal constructor of DeliveryPoint
	 *
	 * @param order       Order of the DeliveryPoint
	 * @param coordinates Coordinates of the deliveryPoint
	 * @throws IllegalArgumentException if the order is null
	 */
	public DeliveryPoint(Order order, Coordinates coordinates) {
		super(coordinates);

		if (order == null)
			throw new IllegalArgumentException("Argument passed \"order\" is null.");
		else
			this.order = order;
	}

	/**
	 * Getter of order
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
        return "DeliveryPoint ["+coordinates.getX()+":"+coordinates.getY()+"] : {" + order.toString() + " , " + super.toString() + "}";
    }

}
