package fr.unice.polytech.dda;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Order
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class OrderTest {

	@Test
	public void testCreateEmptyOrder() {
		Order o = new Order();
		assertEquals(o.getNumberOfProducts(), 0);
	}

	@Test
	public void testAddProductToOrder() {
		Order o = new Order();
		Product p = new Product(20);
		o.addProduct(p, 2);
		assertEquals(2, o.getNumberOfProducts());
	}
}
