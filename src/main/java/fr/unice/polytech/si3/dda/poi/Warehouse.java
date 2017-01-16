package fr.unice.polytech.si3.dda.poi;

import java.util.HashMap;
import java.util.Map;

import fr.unice.polytech.si3.dda.Product;

/**
 * Class Warehouse
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Warehouse implements IPointOfInterest {
	private Map<Product, Integer> stock;
	
	/**
	 * Default constructor of Warehouse
	 */
	public Warehouse(){
		stock = new HashMap<>();
	}
	
	/**
	 * Normal constructor of Warehouse
	 * @param stock Map which contains all of products at this warehouse
	 * @throws IllegalArgumentException if the stock is null
	 */
	public Warehouse(Map<Product, Integer> stock){
		if(stock==null)
			throw new IllegalArgumentException("Argument passed \"stock\" is null.");
		else
			this.stock =  stock;
	}
	
	/**
	 * Allows to add a product at this warehouse
	 * @param product Product to add
	 * @param numberOf Number of copies if this product
	 */
	public void addProduct(Product product, int numberOf){
		if(!stock.containsKey(product))
			stock.put(product, numberOf);
	}
	
	/**
	 * Remove a product at this warehouse
	 * @param product Product to remove one copie
	 */
	public void pullOutProduct(Product product){
		if(stock.containsKey(product))
			stock.put(product, stock.get(product)-1);
	}
	
	/**
	 * Allows to know the number of copies of a product
	 * @param product Product to consult
	 * @return Number of copies of "product"
	 */
	public int howManyProduct(Product product){
		if(stock.containsKey(product))
			return stock.get(product);
		else
			return 0;
	}

	@Override
	/**
	 * Inherited method
	 */
	public boolean isWarehouse() {
		return true;
	}

	@Override
	/**
	 * Inherited method
	 */
	public boolean isDeliveryPoint() {
		return false;
	}
}
