package fr.unice.polytech.si3.dda.scheduler;

import fr.unice.polytech.si3.dda.exception.NonValidCoordinatesException;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;

import java.util.List;

/**
 * Class Context
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Context {

    private final Mapping map;
    private final int maxDrones;
    private final int turns;
    private final int maxPayload;
    private final List<Product> products;
    private Warehouse firstWarehouse;

    /**
     * Context constructor
     *
     * @param builder the context builder
     */
    private Context(ContextBuilder builder) {
        map = builder.map;
        maxDrones = builder.maxDrones;
        turns = builder.turns;
        maxPayload = builder.maxPayload;
        products = builder.products;
        firstWarehouse = builder.firstWarehouse;

    }

    /**
     * Getter for the map
     *
     * @return the map
     */
    public Mapping getMap() {
        return map;
    }

    /**
     * Getter for the number of drones
     *
     * @return the maxDrones
     */
    public int getMaxDrones() {
        return maxDrones;
    }

    /**
     * Getter for the turns
     *
     * @return the turns
     */
    public int getTurns() {
        return turns;
    }

    /**
     * Getter for the max payload
     *
     * @return the maxPayload
     */
    public int getMaxPayload() {
        return maxPayload;
    }

    /**
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Return the warehouse where the drones start.
     *
     * @return the first warehouse.
     */
    public Warehouse getFirstWarehouse() {
        return firstWarehouse;
    }

    /**
     * Class ContextBuilder
     *
     * @author Jeremy JUNAC
     * @author Alexandre HILTCHER
     * @author Pierre RAINERO
     * @author Joël CANCELA VAZ
     */
    public static class ContextBuilder {
        private final Mapping map;
        private final int maxDrones;
        private final int turns;
        private final int maxPayload;
        private List<Product> products;
        private boolean first = true;
        private Warehouse firstWarehouse;

        /**
         * ContextBuilder constructor
         *
         * @param rows       the number of rows of the map
         * @param cols       the number of columns of the map
         * @param maxDrones  the number of drones
         * @param turns      the number of maximum turns
         * @param maxPayload the maximum payload for each drone
         */
        public ContextBuilder(int rows, int cols, int maxDrones, int turns, int maxPayload) {
            map = new Mapping(rows, cols);
            this.maxDrones = maxDrones;
            this.turns = turns;
            this.maxPayload = maxPayload;
        }

        /**
         * Adds products to the new context
         *
         * @param products the products list
         * @return the context builder instance
         */
        public ContextBuilder addProducts(List<Product> products) {
            this.products = products;
            return this;
        }

        /**
         * Adds a warehouse to the new context
         *
         * @param coor  the coordinates of the warehouse
         * @param stock the stock of the warehouse for each product type
         * @return the context builder instance
         * @throws NonValidCoordinatesException if coordinates are invalid
         */
        public ContextBuilder addWarehouse(Coordinates coor, int... stock) throws NonValidCoordinatesException {
            Warehouse w = new Warehouse();
            if (first) {
                firstWarehouse = w;
                first = false;
            }
            for (int i = 0; i < products.size(); i++)
                w.addProduct(products.get(i), stock[i]);
            map.addWarehouse(coor, w);
            return this;
        }

        /**
         * Adds a delivery point to the new context
         *
         * @param coor the coordinates of the delivery point
         * @param o    the order of the delivery point
         * @return the context builder instance
         * @throws NonValidCoordinatesException if coordinates are invalid
         */
        public ContextBuilder addDeliveryPoint(Coordinates coor, Order o) throws NonValidCoordinatesException {
            map.addDeliveryPoint(coor, new DeliveryPoint(o));
            return this;
        }

        /**
         * Builds a context from the current ContextBuilder instance
         *
         * @return a new Context
         */
        public Context build() {
            return new Context(this);
        }

    }

}
