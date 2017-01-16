package fr.unice.polytech.dda.poi;

import fr.unice.polytech.dda.Order;
import fr.unice.polytech.dda.poi.IPointOfInterest;

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
	public DeliveryPoint() throws IllegalArgumentException{
		this(null);
	}
	
	/**
	 * Normal constructor of DeliveryPoint
	 * @param order Order of the DeliveryPoint
	 * @throws IllegalArgumentException if the order is null
	 */
	public DeliveryPoint(Order order) throws IllegalArgumentException{
		if(order==null)
			throw new IllegalArgumentException("Argument passed \"order\" is null.");
		
		 this.order =  order;
	}
	
	/**
	 * Getter of order
	 * @return Order of the DeliveryPoint
	 */
	public Order getOrder(){
		return order;
	}
	
	/**
	 * Inherited method
	 */
	public boolean isWarehouse() {
		return false;
	}

	/**
	 * Inherited method
	 */
	public boolean isDeliveryPoint() {
		return true;
	}

}
