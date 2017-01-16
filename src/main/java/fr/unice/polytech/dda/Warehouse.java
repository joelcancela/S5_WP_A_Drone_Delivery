package fr.unice.polytech.dda;

import java.util.HashMap;
import java.util.Map;

public class Warehouse implements IPointOfInterest {
	private Map<Product, Integer> stock;
	
	public Warehouse(){
		stock = new HashMap<Product, Integer>();
	}
	
	public Warehouse(Map<Product, Integer> stock) throws WrongArgumentException{
		if(stock==null)
			throw new WrongArgumentException("Argument passed \"stock\" is null.");
			
		this.stock =  stock;
	}
	
	public void addProduct(Product product, int numberOf){
		if(!stock.containsKey(product))
			stock.put(product, numberOf);
	}
	
	public void pullOutProduct(Product product){
		if(stock.containsKey(product))
			stock.put(product, stock.get(product)-1);
	}
	
	public int howManyProduct(Product product){
		if(stock.containsKey(product))
			return stock.get(product);
		else
			return 0;
	}

	public boolean isWarehouse() {
		return true;
	}

	public boolean isDeliveryPoint() {
		return false;
	}
}
