package fr.unice.polytech.si3.dda.scheduler;

import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.util.Coordinates;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by alexh on 16/01/2017.
 */
public class FleetTest {
    private Fleet fleet;

    @Before
    public void setUp() throws Exception {
        fleet = new Fleet(
                new Context.ContextBuilder(4,4,5,25,100)
                .addProducts(new ArrayList<>())
                .addWarehouse(new Coordinates(0,0)).build());
    }

    @Test
    public void getDronesNumber() throws Exception {
        assertEquals(5, fleet.getDronesNumber());
    }

}