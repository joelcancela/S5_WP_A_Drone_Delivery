package fr.unice.polytech.si3.dda.scheduler;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the class drone.
 *
 * @author Alexandre Hiltcher
 */
public class DroneTest {
	Drone drone;

	@Before
	public void setUp() throws Exception {
		drone = new Drone(1000, new Coordinates(0, 0));
	}

	@Test
	public void getMaxPayload() throws Exception {
		assertEquals(1000, drone.getMaxPayload());
	}

	@Test
	public void move() throws Exception {
		drone.move(new Coordinates(1, 1));
		assertEquals(new Coordinates(1, 1), drone.getCoordonates());
	}

	@Test
	public void load() throws Exception {
		Product product = new Product(100);
		drone.load(product);
		assertTrue(drone.getLoadedProducts().contains(product));
		assertEquals(100, drone.getUsedPayload());
	}

	@Test(expected = OverLoadException.class)
	public void loadingError() throws OverLoadException {
		Product product = new Product(10000);
		drone.load(product);
	}

	@Test
	public void unload() throws Exception {
		Product product = new Product(100);
		drone.load(product);
		drone.unload(product);
		assertTrue(drone.getLoadedProducts().isEmpty());
		assertEquals(0, drone.getUsedPayload());
	}

	@Test(expected = ProductNotFoundException.class)
	public void unloadError() throws ProductNotFoundException {
		Product product = new Product(100);
		drone.unload(product);
	}

}