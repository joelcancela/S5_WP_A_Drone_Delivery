package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.poi.PointOfInterest;
import fr.unice.polytech.si3.dda.util.Pair;

import java.util.ArrayList;
import java.util.List;

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
     * 
     * @param pois
     * @return
     */
    public static Pair<Integer, List<PointOfInterest>> getMinimalCost(List<PointOfInterest> pois){
    	
    	List<PointOfInterest> path =  new ArrayList<PointOfInterest>();
    	int distance = 0;
    	
    	path.add(pois.get(0));
    	pois.remove(0);
    	int mini = 0;
    	
    	for(int i=0; i<pois.size(); i++){
    		distance += mini;
    		mini = Integer.MAX_VALUE;
    		int index = 0;
    		for(int y=0; y<pois.size(); y++){
        		if(i==y)
        			continue;
        		
        		int current = pois.get(i).distance(pois.get(y));
        		if(current<=mini){
        			mini = current;
        			index = y;
        		}
        	}
    		path.add(pois.get(index));
    		pois.remove(index);
    	}
    	System.out.println(path);
    	return new Pair<Integer, List<PointOfInterest>>(distance, path);
    }

}
