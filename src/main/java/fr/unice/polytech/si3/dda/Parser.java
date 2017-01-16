package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.Context.ContextBuilder;
import fr.unice.polytech.si3.dda.exception.EmptyFileException;
import fr.unice.polytech.si3.dda.exception.NonValidCoordinatesException;
import fr.unice.polytech.si3.dda.util.Coordinates;
import fr.unice.polytech.si3.dda.util.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
	
	private BufferedReader br;
	
	public Parser(String filename) throws FileNotFoundException {
		br = new BufferedReader(new FileReader(filename));
	}
	
	public Context parse() throws IOException, NonValidCoordinatesException, EmptyFileException {
		
		// Parse the first line
		ContextBuilder cb = parseFirstLine();
		// Parse products
		List<Product> products = parseProducts();
		cb.products(products);
		// Parse warehouses
		for(Map.Entry<Coordinates, int[]> entry: parseWarehouses().entrySet())
			cb.addWarehouse(entry.getKey(), entry.getValue());
		// Parse order
		for(Map.Entry<Coordinates, Order> entry: parseOrders(products).entrySet())
			cb.addDeliveryPoint(entry.getKey(), entry.getValue());
		return cb.build();
	}

	private ContextBuilder parseFirstLine() throws EmptyFileException, IOException {
		String line = br.readLine();
		if (line == null)
			throw new EmptyFileException();
		int[] firstArgs = Utils.stringArrayToIntArray(line.split(" "));
		return new Context.ContextBuilder(firstArgs[0], firstArgs[1], firstArgs[2], firstArgs[3], firstArgs[4]);
	}
	
	private List<Product> parseProducts() throws IOException {
		List<Product> products = new ArrayList<>();
		String line = br.readLine();
		// Use of the number of products ?
		line = br.readLine();
		for (String product: line.split(" "))
			products.add(new Product(Integer.parseInt(product)));
		return products;
	}
	
	private Map<Coordinates, int[]> parseWarehouses() throws IOException {
		Map<Coordinates, int[]> products = new HashMap<>();
		String line = br.readLine();
		int numWarehouse = Integer.parseInt(line);
		line = br.readLine();
		for (int i=0; i<numWarehouse; i++) {
			int[] coorInt = Utils.stringArrayToIntArray(line.split(" "));
			Coordinates coor = new Coordinates(coorInt[0], coorInt[1]);
			line = br.readLine();
			int[] stock = Utils.stringArrayToIntArray(line.split(" "));
			products.put(coor, stock);
		}
		return products;
	}
	
	private Map<Coordinates, Order> parseOrders(List<Product> products) throws IOException {
		Map<Coordinates, Order> orders = new HashMap<>();
		String line = br.readLine();
		int numOrder = Integer.parseInt(line);
		for (int i=0; i<numOrder; i++) {
			line = br.readLine();
			Order o = new Order();
			int[] coorInt = Utils.stringArrayToIntArray(line.split(" "));
			Coordinates coor = new Coordinates(coorInt[0], coorInt[1]);
			line = br.readLine();
			int numProduct = Integer.parseInt(line);
			line = br.readLine();
			for(int type: Utils.stringArrayToIntArray(line.split(" ")))
				o.addProduct(products.get(type), 1);
			orders.put(coor, o);
		}
		return orders;
	}
	
}
