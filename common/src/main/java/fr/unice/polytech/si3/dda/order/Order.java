package fr.unice.polytech.si3.dda.order;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
	private List<Product> remaining;
	private int numberOfProducts;


	/**
	 * Order class constructor
	 */
	public Order() {
		products = new LinkedHashMap<>();
		remaining = new ArrayList<>();
		numberOfProducts = 0;
	}

	public Order(Order o) {
		products = new LinkedHashMap<>(o.products);
		remaining = new ArrayList<>(o.remaining);
		numberOfProducts = o.numberOfProducts;
	}

	/**
	 * Adds a product and its quantity to the fr.unice.polytech.si3.dda.exception.order
	 *
	 * @param p        the product to add to the fr.unice.polytech.si3.dda.exception.order
	 * @param quantity the number of the products p ordered
	 */
	public void addProduct(Product p, int quantity) {
		for (int i = 0; i < quantity; i++)
			remaining.add(p);
		if (!products.containsKey(p))
			products.put(p, quantity);
		else
			products.put(p, quantity + products.get(p));
		numberOfProducts += quantity;
	}

	/**
	 * Returns the quantity of a product ordered
	 *
	 * @param p the product you want to know the quantity in the current fr.unice.polytech.si3.dda.exception.order
	 * @return quantity the quantity of the fr.unice.polytech.si3.dda.exception.order product p
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
	public Map<Product, Integer> getProducts() {
		return new LinkedHashMap<>(products);
	}

	/**
	 * Getter for the number of products in the current fr.unice.polytech.si3.dda.exception.order
	 *
	 * @return the number of products in the fr.unice.polytech.si3.dda.exception.order
	 */
	public int getNumberOfProducts() {
		return numberOfProducts;
	}

	/**
	 * Gets the remaining.
	 *
	 * @return the remaining
	 */
	public List<Product> getRemaining() {
		return new ArrayList<>(remaining);
	}

	public boolean isCompleted() {
		return remaining.isEmpty();
	}

	public boolean hasStarted() {
		return remaining.size() < numberOfProducts;
	}

	/**
	 * Remove a product to the list of remaining products
	 *
	 * @param p Product to remove
	 */
	public void deliver(Product p) {
		remaining.remove(p);
	}

	/**
	 * Remove a product to the map of the fr.unice.polytech.si3.dda.exception.order
	 *
	 * @param p Product to remove
	 */
	public void removeThisProduct(Product p) {
		products.put(p, products.get(p) - 1);
	}

	/*
	 * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Order order = (Order) o;

		return products != null ? products.equals(order.products) : order.products == null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return products != null ? products.hashCode() : 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Map.Entry<Product, Integer> p : products.entrySet()) {
			s.append(p.getValue() + "*" + p.getKey().toString() + ", ");
		}
		return s.toString();
	}

	public Order copy() {
		return new Order(this);
	}

}
