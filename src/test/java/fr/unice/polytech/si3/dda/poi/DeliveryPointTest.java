package fr.unice.polytech.si3.dda.poi;

import fr.unice.polytech.si3.dda.Order;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
