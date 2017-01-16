package fr.unice.polytech.si3.dda.scheduler;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.si3.dda.mapping.PointOfInterest;
import fr.unice.polytech.si3.dda.util.Pair;

/**
 * The Graph class is used to represent the graph of routes between the points of interest.
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class PathFinder {
	
	/**
	 * To hide the public one
	 */
	private PathFinder(){
		
	}

    /**
     * Return the sum of the distance between the pois in the order of the list.
     *
     * @param pois the lists of pois.
     * @return the distance.
     */
    public static int getCost(List<PointOfInterest> pois) {
        int distance = 0;
        PointOfInterest poi;
        PointOfInterest next;
        for (int i = 0; i < pois.size() - 1; i++) {
            poi = pois.get(i);
            next = pois.get(i + 1);
            distance += poi.distance(next);
        }
        return distance;
    }
    
    /**
     * Return the path with the minimal cost where the first poi is the start
     * @param pois List of pois to visit
     * @return A pair, contain the cost in first paramter and the path in second parameter
     */
    public static Pair<Integer, List<PointOfInterest>> getMinimalCost(List<PointOfInterest> pois){
    	
    	List<PointOfInterest> path =  new ArrayList<>();
    	int distance = 0;
    	int mini;
    	boolean firstIteration = true;
    	
    	while(pois.size()>1){
    		mini = Integer.MAX_VALUE;
    		if(firstIteration)
    			path.add(pois.get(0));
    		int index = 0;
    		for(int y=1; y<pois.size(); y++){
        		
        		int current = pois.get(0).distance(pois.get(y));
        		if(current<=mini){
        			mini = current;
        			index = y;
        		}
        	}
    		
    		distance = distance + mini;
    		path.add(pois.get(index));
    		
    		pois.set(0, pois.get(index));
    		pois.remove(index);
    		
    		firstIteration = false;
    	}
    	
    	distance = distance + path.get(path.size()-1).distance(pois.get(pois.size()-1));
    	
    	return new Pair<>(distance, path);
    }

}
