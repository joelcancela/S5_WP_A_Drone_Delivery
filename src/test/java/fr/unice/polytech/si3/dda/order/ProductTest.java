package fr.unice.polytech.si3.dda.order;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test class for Product
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class ProductTest {


	@Test
	public void testCreateTwoIdenticalProducts() {
		Product p1 = new Product(10);
		Product p2 = new Product(10);
		assertEquals(p2, p1);
	}

	@Test
	public void testCreateTwoDifferentProducts() {
		Product p1 = new Product(10);
		Product p2 = new Product(20);
		assertNotEquals(p2, p1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateZeroWeightProduct() {
		new Product(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateNegativeWeightProduct() {
		new Product(-20);
	}

	@Test
	public void testDisplayProduct() {
		Product p1 = new Product(100);
		assertEquals("Product{weight=100}", p1.toString());
	}

}
