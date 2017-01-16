package fr.unice.polytech.dda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class x
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Order {
	private List<Product> products = new ArrayList<Product>();


	public Order(Product... p) {
		products = Arrays.asList(p);
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
