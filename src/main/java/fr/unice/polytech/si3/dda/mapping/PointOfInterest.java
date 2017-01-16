package fr.unice.polytech.si3.dda.mapping;

import fr.unice.polytech.si3.dda.util.Coordinates;

/**
 * Class PointOfInterest
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class PointOfInterest {
	protected Coordinates coordinates;

	/**
	 * Default constructor of PointOfInterest
	 */
	public PointOfInterest() {
		this(null);
	}

	/**
	 * Normal constructor of PointOfInterest
	 *
	 * @param coordinates Coordinates of the interest point
	 */
	public PointOfInterest(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * Getter of coordinates
	 *
	 * @return Coordinates of the PointOfInterest
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}

	/**
	 * Setter of coordinates
	 *
	 * @param coordinates news coordinates for this PointOfInterest
	 */
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * Get the distance between two PointOfInterest
	 *
	 * @param poi Second PointOfInterest
	 * @return Distance between two PointOfInterest
	 */
	public int distance(PointOfInterest poi) {
		return (int) Math.ceil(this.getCoordinates().distance(poi.getCoordinates()));
	}

	/**
	 * Use to know if the IPointOfInterest is a warehouse
	 *
	 * @return true if the object is a warehouse, false otherwise
	 */
	public boolean isWarehouse() {
		return false;
	}

	/**
	 * Use to know if the IPointOfInterest is a deliveryPoint
	 *
	 * @return true if the object is a deliveryPoint, false otherwise
	 */
	public boolean isDeliveryPoint() {
		return false;
	}


}
