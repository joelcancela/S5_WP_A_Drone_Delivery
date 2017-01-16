package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.poi.DeliveryPoint;
import fr.unice.polytech.si3.dda.util.Coordinates;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by alexh on 16/01/2017.
 */
public class GraphTest {
    @Test
    public void distance() throws Exception {
        DeliveryPoint d1 = new DeliveryPoint(new Order(), new Coordinates(0, 0));
        DeliveryPoint d2 = new DeliveryPoint(new Order(), new Coordinates(1, 0));
        DeliveryPoint d3 = new DeliveryPoint(new Order(), new Coordinates(0, 1));

        assertEquals(0, Graph.distance(Arrays.asList(d1, d1, d1)));
        assertEquals(3, Graph.distance(Arrays.asList(d1, d2, d3)));

    }

}