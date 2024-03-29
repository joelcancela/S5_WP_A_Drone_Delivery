package fr.unice.polytech.si3.dda.mapping;

import fr.unice.polytech.si3.dda.exception.NonValidCoordinatesException;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.util.Coordinates;
import org.junit.Before;
import org.junit.Test;

public class MappingTest {

	Mapping mapping;
	Warehouse w;
	DeliveryPoint dp;

	@Before
	public void setUp() throws Exception {
		mapping = new Mapping(7, 7);
		mapping.addWarehouse(new Coordinates(0, 0), null);
		w = new Warehouse();
		dp = new DeliveryPoint(new Order(), 0);
	}

	@Test
	public void testAddDeliveryPoint() throws Exception {
		mapping.addDeliveryPoint(new Coordinates(1, 1), null);
	}

	@Test
	public void testAddWarehouse() throws Exception {
		mapping.addWarehouse(new Coordinates(1, 1), null);
	}

	@Test(expected = NonValidCoordinatesException.class)
	public void testAddFail() throws Exception {
		mapping.addDeliveryPoint(new Coordinates(7, 6), null);
	}

	@Test(expected = NonValidCoordinatesException.class)
	public void testAddFail2() throws Exception {
		mapping.addWarehouse(new Coordinates(0, 7), null);
	}

	@Test(expected = NonValidCoordinatesException.class)
	public void testAddFail3() throws Exception {
		mapping.addDeliveryPoint(new Coordinates(-1, 0), null);
	}

	@Test(expected = NonValidCoordinatesException.class)
	public void testAddFail4() throws Exception {
		mapping.addDeliveryPoint(new Coordinates(0, -1), null);
	}

}
