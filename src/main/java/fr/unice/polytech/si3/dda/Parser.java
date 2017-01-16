package fr.unice.polytech.si3.dda;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import fr.unice.polytech.si3.dda.Context.ContextBuilder;
import fr.unice.polytech.si3.dda.exception.NonValidCoordinatesException;
import fr.unice.polytech.si3.dda.util.Coordinates;

public class Parser {
	
	private BufferedReader br;
	
	public Parser(String filename) throws FileNotFoundException {
		br = new BufferedReader(new FileReader(filename));
	}
	
	public Context parse() throws IOException, NonValidCoordinatesException {
		// Parse the first line
		String line = br.readLine();
		String[] firstLine = line.split(" ");
		int[] firstArgs = new int[firstLine.length];
		for (int i = 0; i < firstArgs.length; i++)
			firstArgs[i] = Integer.parseInt(firstLine[i]);
		ContextBuilder cb = new Context.ContextBuilder(firstArgs[0], firstArgs[1], firstArgs[2], firstArgs[3], firstArgs[4]);
		// Parse the products
		line = br.readLine();
		// Use of the number of products ?
		line = br.readLine();
		for (String product: line.split(" "))
			cb.addProduct(new Product(Integer.parseInt(product)));
		// Parse warehouses
		line = br.readLine();
		int numWarehouse = Integer.parseInt(line);
		line = br.readLine();
		for (int i=0; i<numWarehouse; i++) {
			String[] coorStr = line.split(" ");
			Coordinates coor = new Coordinates(Integer.parseInt(coorStr[0]),
					Integer.parseInt(coorStr[1]));
			line = br.readLine();
			int[] stock = new int[firstLine.length];
			for (int j = 0; j < stock.length; j++)
				stock[i] = Integer.parseInt(firstLine[j]);
			cb.addWarehouse(coor, stock);
		}
		
		return cb.build();
	}
	
}
