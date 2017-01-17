package fr.unice.polytech.si3.dda.mapping;

import fr.unice.polytech.si3.dda.exception.NonValidCoordinatesException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.util.Coordinates;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class Mapping
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Mapping {
    private final int rows;
    private final int cols;

    private Map<Coordinates, Warehouse> warehouses;
    private Map<Coordinates, DeliveryPoint> deliveryPoints;

    /**
     * Instantiates a new mapping.
     *
     * @param rows lines' number
     * @param cols columns' number
     */
    public Mapping(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        warehouses = new LinkedHashMap<>();
        deliveryPoints = new LinkedHashMap<>();
    }
    
    public Mapping(Mapping m) {
    	rows = m.rows;
    	cols = m.cols;
    	warehouses = new LinkedHashMap<>();
    	for (Map.Entry<Coordinates, Warehouse> entry : m.warehouses.entrySet())
			warehouses.put(entry.getKey().copy(), entry.getValue().copy());
    	deliveryPoints = new LinkedHashMap<>();
    	for (Entry<Coordinates, DeliveryPoint> entry : m.deliveryPoints.entrySet())
    		deliveryPoints.put(entry.getKey().copy(), entry.getValue().copy());
    }

    /**
     * Return the list of order.
     *
     * @return the list of order.
     */
    public List<Order> getOrders() {
        List<Order> res = new ArrayList<>();
        for (Map.Entry<Coordinates, DeliveryPoint> entry : deliveryPoints.entrySet()) {
            if (entry.getValue().isDeliveryPoint())
                res.add((entry.getValue()).getOrder());
        }
        return res;
    }

    /**
     * Getter for the rows of the map
     *
     * @return the rows of the map (x)
     */
    public int getRows() {
        return rows;
    }

    /**
     * Getter for the columns of the map
     *
     * @return the columns of the map (y)
     */
    public int getCols() {
        return cols;
    }

    /**
     * Return the map containing the warehouses.
     *
     * @return the map containing the warehouse.
     */
    public Map<Coordinates, Warehouse> getWarehouses() {
        return new LinkedHashMap<>(warehouses);
    }

    /**
     * Return the map containing the DeliveryPoint.
     *
     * @return the map containing the delivery points.
     */
    public Map<Coordinates, DeliveryPoint> getDeliveryPoints() {
        return new LinkedHashMap<>(deliveryPoints);
    }

    /**
     * Return the warehouse at the coordinates if it exists.
     *
     * @param coor the coordinates.
     * @return the warehouse.
     */
    public Warehouse getWarehouse(Coordinates coor) {
        return warehouses.get(coor);
    }

    /**
     * Get a warehouse fom a id
     * @param id Id of warehouse searched
     * @return The warehouse found
     * @throws WrongIdException
     */
    public Warehouse getWarehouse(int id) throws WrongIdException {
        for (Warehouse warehouse : warehouses.values())
            if (warehouse.getId() == id) 
            	return warehouse;
        throw new WrongIdException("Wrong id : " + id);
    }

    /**
     * Get a DeliveryPoint fom a id
     * @param id Id of DeliveryPoint searched
     * @return The DeliveryPoint found
     * @throws WrongIdException
     */
    public DeliveryPoint getDeliveryPoint(int id) throws WrongIdException {
        for (DeliveryPoint deliveryPoint : deliveryPoints.values())
            if (deliveryPoint.getId() == id) 
            	return deliveryPoint;
        throw new WrongIdException("Wrong id : " + id);
    }

    /**
     * Return the delivery point at the coordinates if it exists
     *
     * @param coor the coordinates
     * @return the delivery point
     */
    public DeliveryPoint getDeliveryPoint(Coordinates coor) {
        return deliveryPoints.get(coor);
    }

    /**
     * Add a warehouse to the map.
     *
     * @param coor
     * @param warehouse
     * @throws NonValidCoordinatesException
     */
    public void addWarehouse(Coordinates coor, Warehouse warehouse) throws NonValidCoordinatesException {
        if (!checkCoor(coor))
            throw new NonValidCoordinatesException();
        warehouses.put(coor, warehouse);
    }

    /**
     * Add a Delivery point to the map.
     *
     * @param coor
     * @param deliveryPoint
     * @throws NonValidCoordinatesException
     */
    public void addDeliveryPoint(Coordinates coor, DeliveryPoint deliveryPoint) throws NonValidCoordinatesException {
        if (!checkCoor(coor))
            throw new NonValidCoordinatesException();
        deliveryPoints.put(coor, deliveryPoint);
    }

    /**
     * Check coor.
     *
     * @param coor the coor to test
     * @return true, if successful
     */
    private boolean checkCoor(Coordinates coor) {
        if (coor.getX() >= 0 && coor.getY() >= 0 && coor.getX() < cols && coor.getY() < rows)
            return true;
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cols;
        result = prime * result + ((warehouses == null) ? 0 : warehouses.hashCode());
        result = prime * result + ((deliveryPoints == null) ? 0 : deliveryPoints.hashCode());
        result = prime * result + rows;
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Mapping other = (Mapping) obj;
        if (cols != other.cols)
            return false;
        if (warehouses == null) {
            if (other.warehouses != null)
                return false;
        } else if (!warehouses.equals(other.warehouses))
            return false;
        if (deliveryPoints == null) {
            if (other.deliveryPoints != null)
                return false;
        } else if (!deliveryPoints.equals(other.deliveryPoints))
            return false;
        if (rows != other.rows)
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Mapping [rows=" + rows + ", cols=" + cols + ", warehouses=" + warehouses + ", delivery points = " + deliveryPoints + "]";
    }
    
    public Mapping copy() {
    	return new Mapping(this);
    }

}