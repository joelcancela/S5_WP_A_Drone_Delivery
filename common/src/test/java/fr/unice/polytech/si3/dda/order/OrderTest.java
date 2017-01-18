package fr.unice.polytech.si3.dda.order;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test class for Order
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class OrderTest {
    @Test
    public void testEquals() throws Exception {
        Order order = new Order();
        Order order1 = new Order();

        order1.addProduct(new Product(150, 1), 2);

        assertEquals(order, order);
        assertNotEquals(order, order1);
        assertEquals(order1, order1);
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("Order{products={}}", new Order().toString());
        Order order = new Order();
        order.addProduct(new Product(150, 0), 3);
        assertEquals("Order{products={Product [weight=150, id=0]=3}}", order.toString());
    }

    @Test
    public void testCreateEmptyOrder() {
        Order o = new Order();
        assertEquals(o.getNumberOfProducts(), 0);
    }

    @Test
    public void testAddProductToOrder() {
        Order o = new Order();
        Product p = new Product(20, 0);
        o.addProduct(p, 2);
        assertEquals(2, o.getNumberOfProducts());
    }


}
