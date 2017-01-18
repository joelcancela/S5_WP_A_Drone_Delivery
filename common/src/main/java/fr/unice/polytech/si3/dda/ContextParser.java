package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Context.ContextBuilder;
import fr.unice.polytech.si3.dda.exception.EmptyFileException;
import fr.unice.polytech.si3.dda.exception.MalformedContextBodyException;
import fr.unice.polytech.si3.dda.exception.MalformedContextException;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;
import fr.unice.polytech.si3.dda.util.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class ContextParser
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class ContextParser {

	private BufferedReader br;

	/**
	 * ContextParser constructor
	 *
	 * @param filename the filename to parse
	 * @throws FileNotFoundException if the filename is incorrect
	 */
	public ContextParser(String filename) throws FileNotFoundException {
		br = new BufferedReader(new FileReader(filename));
	}

	/**
	 * Parses a file and creates a context
	 *
	 * @return a context with the data parsed from the file
	 * @throws Exception if you can't read the file
	 */
	public Context parse() throws Exception {
		// Parse the first line
		ContextBuilder cb = parseFirstLine();
		try {
			// Parse products
			List<Product> products = parseProducts();
			cb.addProducts(products);
			// Parse warehouses
			for (Map.Entry<Coordinates, int[]> entry : parseWarehouses().entrySet())
				cb.addWarehouse(entry.getKey(), entry.getValue());
			// Parse fr.unice.polytech.si3.dda.exception.order
			for (Map.Entry<Coordinates, Order> entry : parseOrders(products).entrySet())
				cb.addDeliveryPoint(entry.getKey(), entry.getValue());
		} catch (MalformedContextException | ArrayIndexOutOfBoundsException | NullPointerException e) {
			throw new MalformedContextBodyException(cb.build());
		}
		return cb.build();
	}

	/**
	 * Parses first line of the file and creates a context.
	 *
	 * @return ContextBuilder instance
	 * @throws EmptyFileException        if the file is empty
	 * @throws IOException               if you can't read the file
	 * @throws MalformedContextException if the context is malformed
	 */
	private ContextBuilder parseFirstLine() throws Exception {
		try {
			String line = br.readLine();
			if (line == null)
				throw new EmptyFileException();
			int[] firstArgs = Utils.stringArrayToIntArray(line.split(" "));
			if (firstArgs.length == 5) {
				try {
					return new Context.ContextBuilder(firstArgs[0], firstArgs[1], firstArgs[2], firstArgs[3], firstArgs[4]);
				} catch (IndexOutOfBoundsException e) {
				}
			}
		} catch (NumberFormatException | NullPointerException e) {
		}
		throw new MalformedContextException();
	}

	/**
	 * Parses products lines
	 *
	 * @return ArrayList of the products
	 * @throws IOException               if you can't read the file
	 * @throws MalformedContextException if the context is malformed
	 */
	private List<Product> parseProducts() throws Exception {
		try {
			int productId = 0;
			List<Product> products = new ArrayList<>();
			String line = br.readLine();
			int numProducts = Integer.parseInt(line);
			line = br.readLine();
			for (String product : line.split(" ")) {
				products.add(new Product(Integer.parseInt(product), productId));
				productId += 1;
			}
			if (products.size() == numProducts)
				return products;
		} catch (NumberFormatException | NullPointerException e) {
		}
		throw new MalformedContextException();
	}

	/**
	 * Parses warehouses
	 *
	 * @return HashMap of coordinates and int array (stock)
	 * @throws IOException               if you can't read the file
	 * @throws MalformedContextException if the context is malformed
	 */
	private Map<Coordinates, int[]> parseWarehouses() throws Exception {
		try {
			Map<Coordinates, int[]> warehouses = new LinkedHashMap<>();
			String line = br.readLine();
			int numWarehouse = Integer.parseInt(line);
			for (int i = 0; i < numWarehouse; i++) {
				line = br.readLine();
				int[] coorInt = Utils.stringArrayToIntArray(line.split(" "));
				Coordinates coor = new Coordinates(coorInt[0], coorInt[1]);
				line = br.readLine();
				int[] stock = Utils.stringArrayToIntArray(line.split(" "));
				warehouses.put(coor, stock);
			}
			if (numWarehouse == warehouses.size())
				return warehouses;
		} catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
		}
		throw new MalformedContextException();
	}

	/**
	 * Parses orders
	 *
	 * @param products the list of products
	 * @return HashMap of coordinates and orders
	 * @throws IOException               if you can't read the file
	 * @throws MalformedContextException if the context is malformed
	 */
	private Map<Coordinates, Order> parseOrders(List<Product> products) throws Exception {
		try {
			Map<Coordinates, Order> orders = new LinkedHashMap<>();
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
				if (o.getNumberOfProducts() != numProduct)
					throw new MalformedContextException();
			}
			return orders;
		} catch (Exception e) {
			throw new MalformedContextException();
		}
	}

}
