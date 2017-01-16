package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.poi.PointOfInterest;
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

    private void move(Coordinates newCoordinates) {
        coordinates = newCoordinates;
    }

    /**
     * Move the drone to the coordinate of the point of interest.
     *
     * @param poi the point to move the drone.
     */
    public void moveTo(PointOfInterest poi) {
        move(poi.getCoordinates());
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
}
