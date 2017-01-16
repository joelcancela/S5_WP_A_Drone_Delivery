package fr.unice.polytech.si3.dda.poi;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.si3.dda.util.Coordinates;


public class PointOfInterestTest {
	private PointOfInterest pointOfInterest;	
	
    @Before
    public void defineContext() {
    	pointOfInterest = new PointOfInterest();
    }
    
    @Test
    public void getSomeDistances(){
    	pointOfInterest.setCoordinates(new Coordinates(0, 0));
    	PointOfInterest pointOfInterest2 = new PointOfInterest(new Coordinates(0, 2));
    	
    	assertEquals(2,pointOfInterest.distance(pointOfInterest2));
    	pointOfInterest2.setCoordinates(new Coordinates(2, 0));
    	assertEquals(2,pointOfInterest.distance(pointOfInterest2));
    	
    	pointOfInterest.setCoordinates(new Coordinates(1, 3));
    	pointOfInterest2.setCoordinates(new Coordinates(2, 6));
    	assertEquals(4,pointOfInterest.distance(pointOfInterest2));
    }
    
    @Test
    public void whatTypeOfPOI(){
    	assertFalse(pointOfInterest.isWarehouse());
    	assertFalse(pointOfInterest.isDeliveryPoint());
    }
}
