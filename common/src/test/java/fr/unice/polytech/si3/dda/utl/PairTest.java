package fr.unice.polytech.si3.dda.utl;

import fr.unice.polytech.si3.dda.util.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by alexh on 16/01/2017.
 */
public class PairTest {

    @Test
    public void testToString() throws Exception {
        Object o1 = new Object();
        Object o2 = new Object();

        assertEquals("Pair [first=" + o1 + ", second=" + o2 + "]",
                new Pair<Object, Object>(o1, o2).toString());
    }

    @Test
    public void equals() throws Exception {
        Object object1 = new Object();
        Object object2 = new Object();
        Pair<Object, Object> pair = new Pair<Object, Object>(object1, object1);
        Pair<Object, Object> pair1 = new Pair<Object, Object>(object1, object2);
        Pair<Object, Object> pair2 = new Pair<Object, Object>(object2, object1);
        Pair<Object, Object> pair3 = new Pair<Object, Object>(object2, object2);
        Pair<Object, Object> pair4 = new Pair<Object, Object>(object1, object1);
        Pair<Object, Object> pair5 = new Pair<Object, Object>(null, object1);
        Pair<Object, Object> pair6 = new Pair<Object, Object>(object1, null);
        assertTrue(pair.equals(pair));
        assertTrue(!pair.equals(pair1));
        assertTrue(!pair.equals(pair2));
        assertTrue(!pair.equals(pair3));
        assertTrue(!pair.equals(pair5));
        assertTrue(!pair.equals(pair6));
        assertTrue(!pair1.equals(pair));
        assertTrue(pair1.equals(pair1));
        assertTrue(!pair1.equals(pair2));
        assertTrue(!pair1.equals(pair3));
        assertTrue(!pair1.equals(pair4));
        assertTrue(!pair2.equals(pair));
        assertTrue(!pair2.equals(pair1));
        assertTrue(pair2.equals(pair2));
        assertTrue(!pair2.equals(pair3));
        assertTrue(!pair2.equals(pair4));
        assertTrue(pair3.equals(pair3));
        assertTrue(!pair1.equals(new Object()));
        assertTrue(!pair1.equals(null));
        assertTrue(!pair5.equals(pair));
        assertTrue(!pair6.equals(pair));


    }



}