package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.poi.DeliveryPoint;
import fr.unice.polytech.si3.dda.poi.PointOfInterest;
import fr.unice.polytech.si3.dda.util.Coordinates;
import fr.unice.polytech.si3.dda.util.Pair;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * PathFinder test
 * 
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class PathFinderTest {
	
    @Test
    public void distance() throws Exception {
        DeliveryPoint d1 = new DeliveryPoint(new Order(), new Coordinates(0, 0));
        DeliveryPoint d2 = new DeliveryPoint(new Order(), new Coordinates(1, 0));
        DeliveryPoint d3 = new DeliveryPoint(new Order(), new Coordinates(0, 1));

        assertEquals(0, PathFinder.getCost(Arrays.asList(d1, d1, d1)));
        assertEquals(3, PathFinder.getCost(Arrays.asList(d1, d2, d3)));
    }
    
    @Test
    public void getMinimalCost() throws Exception {
        DeliveryPoint d1 = new DeliveryPoint(new Order(), new Coordinates(0, 0));
        DeliveryPoint d2 = new DeliveryPoint(new Order(), new Coordinates(1, 0));
        DeliveryPoint d3 = new DeliveryPoint(new Order(), new Coordinates(0, 1));
        
        List<PointOfInterest> listeOfPois = new ArrayList<PointOfInterest>();
        listeOfPois.add(d1);
        listeOfPois.add(d1);
        listeOfPois.add(d1);
        
        Pair<Integer, List<PointOfInterest>> pair = PathFinder.getMinimalCost(listeOfPois);
        assertEquals((Integer)0, pair.getFirst());
        
        listeOfPois = new ArrayList<PointOfInterest>();
        listeOfPois.add(d1);
        listeOfPois.add(d2);
        listeOfPois.add(d3);
        pair = PathFinder.getMinimalCost(listeOfPois);
        assertEquals((Integer)3, pair.getFirst());
    }

}