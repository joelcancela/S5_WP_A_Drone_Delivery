package fr.unice.polytech.si3.dda;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.si3.dda.Context.ContextBuilder;
import fr.unice.polytech.si3.dda.exception.NonValidCoordinatesException;
import fr.unice.polytech.si3.dda.util.Coordinates;
import fr.unice.polytech.si3.dda.util.Utils;

public class Parser {
	
	private BufferedReader br;
	
	public Parser(String filename) throws FileNotFoundException {
		br = new BufferedReader(new FileReader(filename));
	}
	
	public Context parse() throws IOException, NonValidCoordinatesException {
		List<Product> products = new ArrayList<>();
		
		// Parse the first line
		String line = br.readLine();
		int[] firstArgs = Utils.stringArrayToIntArray(line.split(" "));
		ContextBuilder cb = new Context.ContextBuilder(firstArgs[0], firstArgs[1], firstArgs[2], firstArgs[3], firstArgs[4]);
		// Parse the products
		line = br.readLine();
		// Use of the number of products ?
		line = br.readLine();
		for (String product: line.split(" "))
			products.add(new Product(Integer.parseInt(product)));
		cb.products(products);
		// Parse warehouses
		line = br.readLine();
		int numWarehouse = Integer.parseInt(line);
		line = br.readLine();
		for (int i=0; i<numWarehouse; i++) {
			int[] coorInt = Utils.stringArrayToIntArray(line.split(" "));
			Coordinates coor = new Coordinates(coorInt[0], coorInt[1]);
			line = br.readLine();
			int[] stock = Utils.stringArrayToIntArray(line.split(" "));
			cb.addWarehouse(coor, stock);
		}
		// Parse order
		line = br.readLine();
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
		}
		return cb.build();
	}
	
}
