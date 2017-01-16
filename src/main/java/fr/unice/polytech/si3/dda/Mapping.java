package fr.unice.polytech.si3.dda;

import java.util.HashMap;
import java.util.Map;

import fr.unice.polytech.si3.dda.exception.NonValidCoordinatesException;
import fr.unice.polytech.si3.dda.poi.PointOfInterest;
import fr.unice.polytech.si3.dda.util.Coordinates;

/**
 * Class Product
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
	 * @param coor
	 *            the coor
	 * @return the point of interest
	 */
	public PointOfInterest getPointOfInterest(Coordinates coor) {
		return map.get(coor);
	}

	/**
	 * Adds a point of interest.
	 *
	 * @param coor            the coor of the poi
	 * @param poi            the poi to add
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cols;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		result = prime * result + rows;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mapping other = (Mapping) obj;
		if (cols != other.cols)
			return false;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		if (rows != other.rows)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Mapping [rows=" + rows + ", cols=" + cols + ", map=" + map + "]";
	}

}
