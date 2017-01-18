package fr.unice.polytech.si3.dda.common;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.si3.dda.mapping.Warehouse;

/**
 * The Fleet class handle the fleet of drones. All drones are store in a list.
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Fleet {
    private List<Drone> drones;

    /**
     * Constructs a default fleet from the context.
     *
     * @param maxDrones the maximum number of drones.
     * @param maxPayload the max payload of a drone.
     * @param firstWarehouse the first warehouse.
     */
    public Fleet(int maxDrones, int maxPayload, Warehouse firstWarehouse) {
        drones = new ArrayList<>();

        for (int i = 0; i < maxDrones; i++) {
            drones.add(new Drone(maxPayload, firstWarehouse.getCoordinates()));
        }
    }

    /**
     * Return the number of drone contained by the fleet.
     *
     * @return the number of drone contaioned by the fleet.
     */
    public int getDronesNumber() {
        return drones.size();
    }

    /**
     * Return the drone at index i.
     *
     * @param i the index of the drone.
     * @return the drone at index i.
     */
    public Drone getDrone(int i) {
        return drones.get(i);
    }
    
    public List<Drone> getDrones() {
    	return new ArrayList<>(drones);
    }


}
