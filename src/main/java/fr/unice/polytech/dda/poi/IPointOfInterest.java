package fr.unice.polytech.dda.poi;

/**
 * Interface IPointOfInterest
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public interface IPointOfInterest {
	
	/**
	 * Use to know if the IPointOfInterest is a warehouse
	 * @return true if the object is a warehouse, false otherwise
	 */
	boolean isWarehouse();
	
	/**
	 * Use to know if the IPointOfInterest is a deliveryPoint
	 * @return true if the object is a deliveryPoint, false otherwise
	 */
	boolean isDeliveryPoint();

}
