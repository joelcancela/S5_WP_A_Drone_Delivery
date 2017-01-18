package fr.unice.polytech.si3.dda.mapping;

import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Warehouse
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Warehouse extends PointOfInterest {
    private Map<Product, Integer> stock;

    /**
     * Default constructor of Warehouse
     */
    public Warehouse() {
        stock = new HashMap<>();
    }
    
    public Warehouse(Warehouse w) {
    	super(w.getCoordinates(), w.getId());
    	stock = new HashMap<>(w.stock);
    }

    /**
     * Normal constructor of Warehouse
     *
     * @param stock Map which contains all of products at this warehouse
     * @throws IllegalArgumentException if the stock is null
     */
    public Warehouse(Map<Product, Integer> stock) {
        this(stock, null, 0);
    }

    /**
     * Normal constructor of Warehouse
     *
     * @param coordinates Coordinates of the warehous
	 * @param id Id of the Warehouse
     * @throws IllegalArgumentException if the stock is null
     */
    public Warehouse(Coordinates coordinates, int id) {
        this(new HashMap<>(), coordinates, id);
    }

    /**
     * Normal constructor of Warehouse
     *
     * @param stock       Map which contains all of products at this warehouse
     * @param coordinates Coordinates of the warehouse
	 * @param id Id of the Warehouse
     * @throws IllegalArgumentException if the stock is null
     */
    public Warehouse(Map<Product, Integer> stock, Coordinates coordinates, int id) {
        super(coordinates, id);

        if (stock == null)
            throw new IllegalArgumentException("Argument passed \"stock\" is null.");
        else
            this.stock = stock;
    }

    @Override
    /*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
    public String toString() {
        return "Warehouse : id = " + id + ", coor : [" + coordinates.getX() + ":" + coordinates.getY() + "] : {" + stock.toString() + " , " + super.toString() + "}";
    }

    /**
     * Allows to add a product at this warehouse
     *
     * @param product  Product to add
     * @param numberOf Number of copies if this product
     */
    public void addProduct(Product product, int numberOf) {
        if (!stock.containsKey(product))
            stock.put(product, numberOf);
        else
        	stock.put(product, stock.get(product) + numberOf);
    }

    /**
     * Remove a product at this warehouse.
     *
     * @param product Product to remove one copie
     * @throws ProductNotFoundException if the product is not found
     */
    public void pullOutProduct(Product product) throws ProductNotFoundException {
        if (!stock.containsKey(product) || stock.get(product)<=0)
        	throw new ProductNotFoundException();
        stock.put(product, stock.get(product) - 1);
        	
    }

    /**
     * Allows to know the number of copies of a product
     *
     * @param product Product to consult
     * @return Number of copies of "product"
     */
    public int howManyProduct(Product product) {
        if (stock.containsKey(product))
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

    /**
     * Create a copy of a warehouse
     * @return Same content, different object
     */
    public Warehouse copy() {
    	return new Warehouse(this);
    }
    
    /**
     * Accesor of stock
     * @return The current stock of this warehouse
     */
    public Map<Product, Integer> getStock(){
    	return stock;
    }
    
}
