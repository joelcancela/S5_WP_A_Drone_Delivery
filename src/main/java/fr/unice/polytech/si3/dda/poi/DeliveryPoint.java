package fr.unice.polytech.si3.dda.poi;

import fr.unice.polytech.si3.dda.Order;
import fr.unice.polytech.si3.dda.poi.IPointOfInterest;

/**
 * Class DeliveryPoint
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class DeliveryPoint implements IPointOfInterest {
	private Order order;
	
	/**
	 * Default constructor of DeliveryPoint
	 */
	public DeliveryPoint(){
		this(null);
	}
	
	/**
	 * Normal constructor of DeliveryPoint
	 * @param order Order of the DeliveryPoint
	 * @throws IllegalArgumentException if the order is null
	 */
	public DeliveryPoint(Order order){
		if(order==null)
			throw new IllegalArgumentException("Argument passed \"order\" is null.");
		else
			this.order =  order;
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public String toString() {
		return "DeliveryPoint [order=" + order + "]";
	}

	/**
	 * Getter of order
	 * @return Order of the DeliveryPoint
	 */
	public Order getOrder(){
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

}
