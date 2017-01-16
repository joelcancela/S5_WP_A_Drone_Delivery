package fr.unice.polytech.dda;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Order
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Order {
	private Map<Product, Integer> products;
	private int numberOfProducts;


	/**
	 * Order class constructor
	 */
	public Order() {
		products = new HashMap<>();
		numberOfProducts = 0;
	}

	/**
	 * Adds a product and its quantity to the order
	 *
	 * @param p        the product to add to the order
	 * @param quantity the number of the products p ordered
	 */
	public void addProduct(Product p, int quantity) {
		products.put(p, quantity);
		numberOfProducts = numberOfProducts + quantity; //Fixme maybe a problem if the product is already in the hashmap and replaced
	}

	/**
	 * Returns the quantity of a product ordered
	 *
	 * @param p the product you want to know the quantity in the current order
	 * @return quantity the quantity of the order product p
	 */
	public int getQuantity(Product p) {
		if (products.containsKey(p)) {
			return products.get(p);
		}
		return 0;
	}

	/**
	 * Getter for the products orders
	 *
	 * @return the products orders list
	 */
	public Map getProducts() {
		return products;
	}

	/**
	 * Getter for the number of products in the current order
	 *
	 * @return the number of products in the order
	 */
	public int getNumberOfProducts() {
		return numberOfProducts;
	}

	/**
	 * equals method
	 *
	 * @param o object to compare the order with
	 * @return true if the current instance and o are equals, else false
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Order order = (Order) o;

		return products != null ? products.equals(order.products) : order.products == null;
	}

	/**
	 * hashCode method
	 *
	 * @return the hashcode of the current instance
	 */
	@Override
	public int hashCode() {
		return products != null ? products.hashCode() : 0;
	}

	/**
	 * toString method
	 *
	 * @return the representation of the order
	 */
	@Override
	public String toString() {
		return "Order{" +
				"products=" + products +
				'}';
	}
	
}
