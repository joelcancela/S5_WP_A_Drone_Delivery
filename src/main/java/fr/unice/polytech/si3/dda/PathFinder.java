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

    public int distance(List<PointOfInterest> pois){
       int distance = 0;
       int index = 0;
       PointOfInterest poi = pois.get(index);
       return distance;
    }
    
    public static Pair<Integer, List<PointOfInterest>> getMinimalCost(List<PointOfInterest> pois){
    	List<PointOfInterest> path = new ArrayList<>();
    	int totalDistance = 0;
    	int miniDistance = 0;
    	
    	path.add(pois.get(0));
    	
    	for(int i=0; i<pois.size(); i++){
    		totalDistance += miniDistance;
    		miniDistance=0;
    		PointOfInterest tempo = null;
    		for(int y=0; y<pois.size(); y++){
    			if(i==y)
    				continue;
    			
    			int currentDistance  = pois.get(i).distance(pois.get(y));
    			if(miniDistance>currentDistance){
    				miniDistance=currentDistance;
    				tempo = pois.get(y);
    			}
    		}
    		
    		path.add(tempo);
    	}
    	
    	return new Pair<Integer, List<PointOfInterest>>(totalDistance, path);
    }

}
