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

    /**
     * Adds a product and its quantity to the order
     *
     * @param p        the product to add to the order
     * @param quantity the number of the products p ordered
     */
    public void addProduct(Product p, int quantity) {
        if (products.containsKey(p)) {
            numberOfProducts = numberOfProducts - products.get(p);
        }
        products.put(p, quantity);
        numberOfProducts = numberOfProducts + quantity;
        for (int i=0; i<quantity; i++)
        	remaining.add(p);
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
    public Map<Product, Integer> getProducts() {
        return new LinkedHashMap<>(products);
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
	 * Gets the remaining.
	 *
	 * @return the remaining
	 */
	public List<Product> getRemaining() {
		return new ArrayList<>(remaining);
	}
	
	public void deliver(Product p) {
		remaining.remove(p);
	}
	
	public void removeThisProduct(Product p){
		products.put(p, products.get(p)-1);
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
        return "Order{" +
                "products=" + products +
                '}';
    }

}
