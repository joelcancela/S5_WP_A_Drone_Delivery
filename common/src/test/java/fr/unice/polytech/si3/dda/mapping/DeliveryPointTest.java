package fr.unice.polytech.si3.dda.mapping;

import fr.unice.polytech.si3.dda.order.Order;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeliveryPointTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	private DeliveryPoint deliveryPoint;

	@Before
	public void defineContext() {
		deliveryPoint = new DeliveryPoint(new Order(), 0);
	}

	@Test
	public void whatTypeOfPOI() {
		assertTrue(deliveryPoint.isDeliveryPoint());
		assertFalse(deliveryPoint.isWarehouse());
	}

	@Test
	public void nullStock() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Argument passed \"fr.unice.polytech.si3.dda.exception.order\" is null.");

		deliveryPoint = new DeliveryPoint(null, 0);
	}

}
