package fr.unice.polytech.si3.dda;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.si3.dda.exception.NonValidCoordinatesException;
import fr.unice.polytech.si3.dda.poi.DeliveryPoint;
import fr.unice.polytech.si3.dda.poi.Warehouse;
import fr.unice.polytech.si3.dda.util.PairInt;

public class MappingTest {

	Mapping mapping;
	Warehouse w;
	DeliveryPoint dp;
	
	@Before
	public void setUp() throws Exception {
		mapping = new Mapping(7, 7);
		mapping.addPointOfInterest(new PairInt(0,0), null);
		w = new Warehouse();
		dp = new DeliveryPoint(new Order());
	}
	
	@Test
	public void testAddDeliveryPoint() throws Exception {
		mapping.addPointOfInterest(new PairInt(1, 1), null);
	}
	
	@Test
	public void testAddWarehouse() throws Exception {
		mapping.addPointOfInterest(new PairInt(1, 1), null);
	}
	
	@Test(expected=NonValidCoordinatesException.class)
	public void testAddFail() throws Exception {
		mapping.addPointOfInterest(new PairInt(7, 6), null);
	}
	
	@Test(expected=NonValidCoordinatesException.class)
	public void testAddFail2() throws Exception {
		mapping.addPointOfInterest(new PairInt(0, 7), null);
	}
	
	@Test(expected=NonValidCoordinatesException.class)
	public void testAddFail3() throws Exception {
		mapping.addPointOfInterest(new PairInt(-1, 0), null);
	}
	
	@Test(expected=NonValidCoordinatesException.class)
	public void testAddFail4() throws Exception {
		mapping.addPointOfInterest(new PairInt(0, -1), null);
	}

}
