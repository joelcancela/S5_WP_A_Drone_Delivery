package fr.unice.polytech.si3.dda.util;

import fr.unice.polytech.si3.dda.exception.EmptyFileException;
import fr.unice.polytech.si3.dda.exception.NonValidCoordinatesException;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.scheduler.Context.ContextBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class Parser
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Parser {

	private BufferedReader br;

	/**
	 * Parser constructor
	 *
	 * @param filename the filename to parse
	 * @throws FileNotFoundException if the filename is incorrect
	 */
	public Parser(String filename) throws FileNotFoundException {
		br = new BufferedReader(new FileReader(filename));
	}

	/**
	 * Parses a file and creates a context
	 *
	 * @return a context with the data parsed from the file
	 * @throws IOException                  if you can't read the file
	 * @throws NonValidCoordinatesException if the coordinates are invalid
	 * @throws EmptyFileException           if the file is empty
	 */
	public Context parse() throws IOException, NonValidCoordinatesException, EmptyFileException {

		// Parse the first line
		ContextBuilder cb = parseFirstLine();
		// Parse products
		List<Product> products = parseProducts();
		cb.addProducts(products);
		// Parse warehouses
		for (Map.Entry<Coordinates, int[]> entry : parseWarehouses().entrySet())
			cb.addWarehouse(entry.getKey(), entry.getValue());
		// Parse order
		for (Map.Entry<Coordinates, Order> entry : parseOrders(products).entrySet())
			cb.addDeliveryPoint(entry.getKey(), entry.getValue());
		return cb.build();
	}

	/**
	 * Parses first line of the file and creates a context
	 *
	 * @return ContextBuilder instance
	 * @throws EmptyFileException if the file is empty
	 * @throws IOException        if you can't read the file
	 */
	private ContextBuilder parseFirstLine() throws EmptyFileException, IOException {
		String line = br.readLine();
		if (line == null)
			throw new EmptyFileException();
		int[] firstArgs = Utils.stringArrayToIntArray(line.split(" "));
		return new Context.ContextBuilder(firstArgs[0], firstArgs[1], firstArgs[2], firstArgs[3], firstArgs[4]);
	}

	/**
	 * Parses products lines
	 *
	 * @return ArrayList of the products
	 * @throws IOException if you can't read the file
	 */
	private List<Product> parseProducts() throws IOException {
		List<Product> products = new ArrayList<>();
		String line = br.readLine();
		// Use of the number of products ?
		line = br.readLine();
		for (String product : line.split(" "))
			products.add(new Product(Integer.parseInt(product)));
		return products;
	}

	/**
	 * Parses warehouses
	 *
	 * @return HashMap of coordinates and int array (stock)
	 * @throws IOException if you can't read the file
	 */
	private Map<Coordinates, int[]> parseWarehouses() throws IOException {
		Map<Coordinates, int[]> products = new HashMap<>();
		String line = br.readLine();
		int numWarehouse = Integer.parseInt(line);
		line = br.readLine();
		for (int i = 0; i < numWarehouse; i++) {
			int[] coorInt = Utils.stringArrayToIntArray(line.split(" "));
			Coordinates coor = new Coordinates(coorInt[0], coorInt[1]);
			line = br.readLine();
			int[] stock = Utils.stringArrayToIntArray(line.split(" "));
			products.put(coor, stock);
		}
		return products;
	}

	/**
	 * Parses orders
	 *
	 * @param products the list of products
	 * @return HashMap of coordinates and orders
	 * @throws IOException if you can't read the file
	 */
	private Map<Coordinates, Order> parseOrders(List<Product> products) throws IOException {
		Map<Coordinates, Order> orders = new HashMap<>();
		String line = br.readLine();
		int numOrder = Integer.parseInt(line);
		for (int i = 0; i < numOrder; i++) {
			line = br.readLine();
			Order o = new Order();
			int[] coorInt = Utils.stringArrayToIntArray(line.split(" "));
			Coordinates coor = new Coordinates(coorInt[0], coorInt[1]);
			line = br.readLine();
			int numProduct = Integer.parseInt(line);
			line = br.readLine();
			for (int type : Utils.stringArrayToIntArray(line.split(" ")))
				o.addProduct(products.get(type), 1);
			orders.put(coor, o);
		}
		return orders;
	}

}
