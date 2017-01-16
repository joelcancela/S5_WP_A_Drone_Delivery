package fr.unice.polytech.si3.dda.mapping;

import fr.unice.polytech.si3.dda.exception.NonValidCoordinatesException;
import fr.unice.polytech.si3.dda.util.Coordinates;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Mapping
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Mapping {
	private final int rows;
	private final int cols;

	private Map<Coordinates, PointOfInterest> map;

	/**
	 * Instantiates a new mapping.
	 *
	 * @param rows lines' number
	 * @param cols columns' number
	 */
	public Mapping(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		map = new HashMap<>();
	}

	/**
	 * Gets the mapping.
	 *
	 * @return the mapping
	 */
	public Map<Coordinates, PointOfInterest> getMapping() {
		return new HashMap<>(map);
	}

	/**
	 * Gets a point of interest.
	 *
	 * @param coor the coor
	 * @return the point of interest
	 */
	public PointOfInterest getPointOfInterest(Coordinates coor) {
		return map.get(coor);
	}

	/**
	 * Getter for the rows of the map
	 *
	 * @return the rows of the map (x)
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Getter for the columns of the map
	 *
	 * @return the columns of the map (y)
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * Adds a point of interest.
	 *
	 * @param coor the coor of the mapping
	 * @param poi  the mapping to add
	 * @throws NonValidCoordinatesException the non valid coordinates exception
	 */
	public void addPointOfInterest(Coordinates coor, PointOfInterest poi) throws NonValidCoordinatesException {
		if (!checkCoor(coor))
			throw new NonValidCoordinatesException();
		map.put(coor, poi);
	}

	/**
	 * Check coor.
	 *
	 * @param coor the coor to test
	 * @return true, if successful
	 */
	private boolean checkCoor(Coordinates coor) {
		if (coor.getX() >= 0 && coor.getY() >= 0 && coor.getX() < cols && coor.getY() < rows)
			return true;
		return false;
	}

}
