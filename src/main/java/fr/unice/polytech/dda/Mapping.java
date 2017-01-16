package fr.unice.polytech.dda;

import java.util.HashMap;
import java.util.Map;

import fr.unice.polytech.dda.poi.IPointOfInterest;
import fr.unice.polytech.dda.util.PairInt;

public class Mapping {

	private Map<PairInt, IPointOfInterest> mapping;
	
	/**
	 * Instantiates a new mapping.
	 */
	public Mapping() {
		mapping = new HashMap<>();
	}
	
	/**
	 * Gets the mapping.
	 *
	 * @return the mapping
	 */
	public Map<PairInt, IPointOfInterest> getMapping() {
		return mapping;
	}
	
	/**
	 * Gets a point of interest.
	 *
	 * @param coor the coor
	 * @return the point of interest
	 */
	public IPointOfInterest getPointOfInterest(PairInt coor) {
		return mapping.get(coor);
	}
	
}
