package fr.unice.polytech.dda;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import fr.unice.polytech.dda.poi.DeliveryPoint;

public class DeliveryPointTest {
	private DeliveryPoint deliveryPoint;
	
    @Rule
    public ExpectedException thrown = ExpectedException.none();
	
    @Before
    public void defineContext() {
    	deliveryPoint = new DeliveryPoint(new Order());
    }
    
    @Test
    public void whatTypeOfPOI(){
    	assertTrue(deliveryPoint.isDeliveryPoint());
    	assertFalse(deliveryPoint.isWarehouse());
    }
    
    @Test
    public void nullStock(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Argument passed \"order\" is null.");
        
        deliveryPoint = new DeliveryPoint(null);
    }
}
