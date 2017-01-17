package fr.unice.polytech.si3.dda.mapping;

import fr.unice.polytech.si3.dda.order.Product;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class WarehouseTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	private Warehouse warehouse;

	@Before
	public void defineContext() {
		warehouse = new Warehouse();
	}

	@Test
	public void addAProductAndGetIt() {
		Product product1 = new Product(200, 0);
		warehouse.addProduct(product1, 3);

		assertEquals(3, warehouse.howManyProduct(product1));
	}

	@Test
	public void howManyProduct() {
		Product product1 = new Product(200, 0);
		Product product2 = new Product(150, 1);
		warehouse.addProduct(product1, 3);

		assertEquals(3, warehouse.howManyProduct(product1));
		assertEquals(0, warehouse.howManyProduct(product2));

		warehouse.addProduct(product2, 2);
		assertEquals(2, warehouse.howManyProduct(product2));

		warehouse.pullOutProduct(product2);
		assertEquals(1, warehouse.howManyProduct(product2));
	}

	@Test
	public void whatTypeOfPOI() {
		assertTrue(warehouse.isWarehouse());
		assertFalse(warehouse.isDeliveryPoint());
	}

	@Test
	public void createAFullWarehouse() {
		Map<Product, Integer> stock = new HashMap<Product, Integer>();
		Product product1 = new Product(200, 0);
		Product product2 = new Product(150, 1);

		stock.put(product1, 3);
		stock.put(product2, 2);

		warehouse = new Warehouse(stock);
		assertEquals(3, warehouse.howManyProduct(product1));
		assertEquals(2, warehouse.howManyProduct(product2));
	}

	@Test
	public void nullStock() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Argument passed \"stock\" is null.");

		warehouse = new Warehouse(null);
	}
}
