package fr.unice.polytech.si3.dda.scheduler.strategy;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.scheduler.Fleet;
import fr.unice.polytech.si3.dda.util.Coordinates;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SingleDroneStrategyTest {
    private SingleDroneStrategy singleDroneStrategy;

    @Before
    public void setUp() throws Exception {
        List<Product> products = new ArrayList<Product>();
        products.add(new Product(50, 0));
        products.add(new Product(20, 1));
        products.add(new Product(12, 2));
        products.add(new Product(16, 3));

        Order order1 = new Order();
        order1.addProduct(new Product(50, 0), 2);
        order1.addProduct(new Product(12, 2), 1);
        order1.addProduct(new Product(16, 3), 1);

        Order order2 = new Order();
        order2.addProduct(new Product(16, 3), 1);

        Context context = new Context.ContextBuilder(5, 4, 5, 25, 100)
                .addProducts(products)
                .addWarehouse(new Coordinates(0, 0), 1, 2, 3, 4)
                .addWarehouse(new Coordinates(2, 3), 1, 0, 0, 0)
                .addWarehouse(new Coordinates(1, 4), 1, 0, 0, 0)
                .addDeliveryPoint(new Coordinates(1, 1), order1)
                .addDeliveryPoint(new Coordinates(0, 3), order2).build();

        singleDroneStrategy = new SingleDroneStrategy(context);
    }

    @Test
    public void test() throws OverLoadException, ProductNotFoundException {
        singleDroneStrategy.getInstructions();
    }

}
