package fr.unice.polytech.si3.dda;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import fr.unice.polytech.si3.dda.exception.MalformedContextException;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.util.Coordinates;

public class ContextParserTest {
	
    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    String FILE_NAME = "new-file.txt";
    ContextParser p;
    File file;
	
	@Before
	public void setUp() throws Exception {
		file = new File(temp.getRoot(), FILE_NAME);
		BufferedWriter wrt = new BufferedWriter(new FileWriter(file));
		wrt.write("7 5 2 25 500\n"
				+ "3\n"
				+ "100 5 450\n"
				+ "1\n"
				+ "0 0\n"
				+ "5 1 0\n"
				+ "1\n"
				+ "1 1\n"
				+ "2\n"
				+ "2 0\n");
		wrt.close();
		p = new ContextParser(file.getAbsolutePath());
	}

	@Test
	public void testParse() throws Exception {
		//ContextBuilder cb = p.parseFirstLine();
		Context ctx = p.parse();
		Mapping map = new Mapping(7, 5);
		List<Product> products = Arrays.asList(new Product[]{
				new Product(100, 0),
				new Product(5, 1),
				new Product(450, 2)
		});
		Map<Product, Integer> stock = new HashMap<>();
		stock.put(products.get(0), 5);
		stock.put(products.get(1), 1);
		stock.put(products.get(2), 0);
		map.addWarehouse(new Coordinates(0, 0), new Warehouse(stock));
		Order o = new Order();
		o.addProduct(products.get(2), 1);
		o.addProduct(products.get(0), 1);
		map.addDeliveryPoint(new Coordinates(1, 1), new DeliveryPoint(o, 0));
		
		assertEquals(2, ctx.getMaxDrones());
		assertEquals(25, ctx.getTurns());
		assertEquals(500, ctx.getMaxPayload());
		assertEquals(products, ctx.getProducts());
		//TODO: Why ??
		//assertEquals(map, ctx.getMap());
	}
	
	@Test(expected=MalformedContextException.class)
	public void testParseMalformed() throws Exception {
		BufferedWriter wrt = new BufferedWriter(new FileWriter(file));
		wrt.write("7 5 2 25 500a\n"
				+ "3\n"
				+ "100 5 450\n"
				+ "1\n"
				+ "0 0\n"
				+ "5 1 0\n"
				+ "1\n"
				+ "1 1\n"
				+ "2\n"
				+ "2 0\n");
		wrt.close();
		Context ctx = p.parse();
	}
	
	@Test(expected=MalformedContextException.class)
	public void testParseMalformed2() throws Exception {
		BufferedWriter wrt = new BufferedWriter(new FileWriter(file));
		wrt.write("7 5 2 25 500a\n"
				+ "3\n"
				+ "100 5 450\n"
				+ "1\n"
				+ "0 0\n"
				+ "5 1 0\n"
				+ "1\n"
				+ "1 1\n"
				+ "2\n"
				+ "2\n");
		wrt.close();
		Context ctx = p.parse();
	}
	
	@Test(expected=MalformedContextException.class)
	public void testParseMalformed3() throws Exception {
		BufferedWriter wrt = new BufferedWriter(new FileWriter(file));
		wrt.write("7 5 2 25 500a\n"
				+ "3\n"
				+ "100 5 450\n"
				+ "1\n"
				+ "0 0\n"
				+ "5 1 0\n");
		wrt.close();
		Context ctx = p.parse();
	}

}
