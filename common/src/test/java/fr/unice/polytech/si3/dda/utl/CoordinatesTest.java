package fr.unice.polytech.si3.dda.utl;

import static org.junit.Assert.*;

import fr.unice.polytech.si3.dda.util.Coordinates;
import org.junit.Before;
import org.junit.Test;

public class CoordinatesTest {
	private Coordinates coordinates;
	
    @Before
    public void defineContext() {
    	coordinates = new Coordinates(0,0);
    }
    
    @Test
    public void constructorFromCoordinates(){
    	Coordinates coordinates2 = new Coordinates(coordinates);
    	assertEquals(coordinates, coordinates2);
    	assertTrue(coordinates.equals(coordinates2));
    	
    	coordinates2.setSize(1, 2);
    	assertFalse(coordinates.equals(coordinates2));
    }
    
    @Test
    public void plusAndMinus(){
    	Coordinates coordinates2 = new Coordinates(1,1);
    	coordinates.plus(coordinates2);
    	
    	assertEquals(1, coordinates.getX());
    	assertEquals(1, coordinates.getY());
    	
    	coordinates.minus(coordinates2);
    	assertEquals(0, coordinates.getX());
    	assertEquals(0, coordinates.getY());
    }
    
    @Test
    public void distance(){
    	Coordinates coordinates2 = new Coordinates(1,1);

    	int d = (int) coordinates.distance(coordinates2);
    	assertEquals(1, d);
    }
    
    @Test
    public void copie(){
    	Coordinates coordinates2 = new Coordinates(1,1);
    	assertNotEquals(coordinates, coordinates2);
    	assertNotEquals(coordinates.hashCode(), coordinates2.hashCode());
    	
    	coordinates2 = coordinates.copy();
    	assertEquals(coordinates, coordinates2);
    	assertEquals(coordinates.hashCode(), coordinates2.hashCode());
    }
}
