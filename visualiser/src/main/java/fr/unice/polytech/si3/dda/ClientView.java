package fr.unice.polytech.si3.dda;

import java.util.Map;

import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;

public class ClientView extends View {
	
	Order order;

	@Override
	public void display() {
		// Percentage of the order
		// Review of the order and status
		// Remaining time
		 Map<Product, Integer> products = order.getProducts();
		 for (Map.Entry<Product, Integer> entry: products.entrySet()) {
			 
		 }
		
		
	}

}
