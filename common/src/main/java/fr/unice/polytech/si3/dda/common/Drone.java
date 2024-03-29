package fr.unice.polytech.si3.dda.common;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * The Drone class describe the characteristics of the Drone.
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Drone {
	private int maxPayload;
	private int usedPayload;

	private Coordinates coordinates;
	private List<Product> loadedProducts;

	/**
	 * Constructs a default drone with the based data.
	 *
	 * @param maxPayload  the max payload of the drone.
	 * @param coordinates the coordinates of the drone.
	 */
	public Drone(int maxPayload, Coordinates coordinates) {
		this.maxPayload = maxPayload;
		this.coordinates = coordinates;
		this.usedPayload = 0;
		loadedProducts = new ArrayList<>();
	}

	/**
	 * Move the drone by changing its coordinates.
	 *
	 * @param newCoordinates the new coordinates of the drone.
	 */
	public void move(Coordinates newCoordinates) {
		coordinates = newCoordinates;
	}

	/**
	 * Load a product, add it to the list of loaded products and remove its weight to the payload of the drone.
	 *
	 * @param product the product to load.
	 * @throws OverLoadException if the drone is overload when loading the product.
	 */
	public void load(Product product) throws OverLoadException {
		if (product.getWeight() + usedPayload > maxPayload)
			throw new OverLoadException(product.getWeight() + usedPayload + " > " + maxPayload);
		loadedProducts.add(product);
		usedPayload += product.getWeight();
	}

	/**
	 * Unload a product, remove the product of the list and its weight form the payload of the drone.
	 *
	 * @param product the product to unload.
	 * @throws ProductNotFoundException if the product to remove is not loaded in the drone.
	 */
	public void unload(Product product) throws ProductNotFoundException {
		usedPayload -= product.getWeight();
		if (!loadedProducts.contains(product))
			throw new ProductNotFoundException();
		loadedProducts.remove(product);
	}

	/**
	 * Return the max payload of the drone.
	 *
	 * @return the max payload of the drone.
	 */
	public int getMaxPayload() {
		return maxPayload;
	}

	/**
	 * Return the used payload of the drone.
	 *
	 * @return the used payload of the drone.
	 */
	public int getUsedPayload() {
		return usedPayload;
	}

	/**
	 * Return the current coordinates of the drone.
	 *
	 * @return the current coordinates of the drone.
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}

	/**
	 * Return the list of products loaded in the drone.
	 *
	 * @return the products loaded in the drone.
	 */
	public List<Product> getLoadedProducts() {
		return new ArrayList<>(loadedProducts);
	}

	/**
	 * Copy an instance of an object Drone (generate a new one)
	 *
	 * @return An exact copy as a different object
	 */
	public Drone copy() {
		return new Drone(maxPayload, coordinates);
	}

	/**
	 * Return the number of a product carried by this drone
	 *
	 * @param product Product to count
	 * @return The number of a product
	 */
	public int getNumberOf(Product product) {
		if (loadedProducts.contains(product)) {
			int numberOf = 0;
			for (int i = 0; i < loadedProducts.size(); i++)
				numberOf++;
			return numberOf;
		} else
			return 0;

	}

	/**
	 * Allow to known if the drone didn't carry anything
	 *
	 * @return true if we is empty, false otherwise
	 */
	public boolean isEmpty() {
		return usedPayload == 0;
	}


}
