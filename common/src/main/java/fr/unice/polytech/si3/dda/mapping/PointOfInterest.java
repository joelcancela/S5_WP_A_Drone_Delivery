package fr.unice.polytech.si3.dda.mapping;

import fr.unice.polytech.si3.dda.common.Drone;
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
	protected int id;

	/**
	 * Default constructor of PointOfInterest
	 */
	public PointOfInterest() {
		this(null, 0);
	}

	/**
	 * Normal constructor of PointOfInterest
	 *
	 * @param coordinates Coordinates of the interest point
	 * @param id          Id of the PointOfInterest
	 */
	public PointOfInterest(Coordinates coordinates, int id) {
		this.id = id;
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
	 * Return the id of the PointOfInterest.
	 *
	 * @return the id of the piont of interest.
	 */
	public int getId() {
		return id;
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
	 * Get the distance from the drone
	 *
	 * @param drone the drone
	 * @return Distance from the drone
	 */
	public int distance(Drone drone) {
		return (int) Math.ceil(this.getCoordinates().distance(drone.getCoordinates()));
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
