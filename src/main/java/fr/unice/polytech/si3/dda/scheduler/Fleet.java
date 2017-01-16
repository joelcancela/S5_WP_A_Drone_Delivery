package fr.unice.polytech.si3.dda.scheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * The Fleet class handle the fleet of drones. All drones are store in a list.
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Fleet {
    private List<Drone> register;

    /**
     * Constructs a default fleet from the context.
     *
     * @param context the context of execution.
     */
    public Fleet(Context context) {
        register = new ArrayList<>();

        for (int i = 0; i < context.getMaxDrones(); i++) {
            register.add(new Drone(context.getMaxPayload(), context.getFirstWarehouse().getCoordinates()));
        }
    }

    /**
     * Return the number of drone contained by the fleet.
     *
     * @return the number of drone contaioned by the fleet.
     */
    public int getDronesNumber() {
        return register.size();
    }

    /**
     * Return the drone at index i.
     *
     * @param i the index of the drone.
     * @return the drone at index i.
     */
    public Drone getDrone(int i) {
        return register.get(i);
    }


}
