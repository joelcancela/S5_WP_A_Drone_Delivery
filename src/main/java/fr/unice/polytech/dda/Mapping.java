package fr.unice.polytech.dda;

import java.util.HashMap;
import java.util.Map;

import fr.unice.polytech.dda.util.PairInt;

/**
 * Class Product
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Mapping {

	public final int rows, cols;

	private Map<PairInt, IPointOfInterest> mapping;

	/**
	 * Instantiates a new mapping.
	 */
	public Mapping(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		mapping = new HashMap<>();
	}

	/**
	 * Gets the mapping.
	 *
	 * @return the mapping
	 */
	public Map<PairInt, IPointOfInterest> getMapping() {
		return new HashMap<>(mapping);
	}

	/**
	 * Gets a point of interest.
	 *
	 * @param coor
	 *            the coor
	 * @return the point of interest
	 */
	public IPointOfInterest getPointOfInterest(PairInt coor) {
		return mapping.get(coor);
	}

	/**
	 * Adds a point of interest.
	 *
	 * @param coor
	 *            the coor of the poi
	 * @param poi
	 *            the poi to add
	 */
	public void addPointOfInterest(PairInt coor, IPointOfInterest poi) {
		checkCoor(coor);
		mapping.put(coor, poi);
	}

	/**
	 * Check coor.
	 *
	 * @param coor the coor to test
	 * @return true, if successful
	 */
	private boolean checkCoor(PairInt coor) {
		if (coor.getX() >= 0 && coor.getY() > 0 && coor.getX() < cols && coor.getY() < rows)
			return true;
		return false;
	}

}
