package fr.unice.polytech.si3.dda.scheduler.strategy;

import fr.unice.polytech.si3.dda.exception.NonValidCoordinatesException;
import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.instruction.DeliverInstruction;
import fr.unice.polytech.si3.dda.instruction.IInstruction;
import fr.unice.polytech.si3.dda.instruction.LoadInstruction;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.util.Coordinates;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleDroneStrategyTest {
    private SingleDroneStrategy singleDroneStrategy;

    @Test
    public void finishAOrderAndMoveToNearestWarehouse() throws OverLoadException, ProductNotFoundException, NonValidCoordinatesException {
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
        
        List<IInstruction> expected = new ArrayList<IInstruction>();
        expected.add(new LoadInstruction(0, 0, 0, 1));
        expected.add(new LoadInstruction(0, 0, 2, 1));
        expected.add(new LoadInstruction(0, 0, 3, 2));
        expected.add(new DeliverInstruction(0, 0, 3, 1));
        expected.add(new DeliverInstruction(0, 0, 0, 1));
        expected.add(new DeliverInstruction(0, 0, 2, 1));
        expected.add(new DeliverInstruction(0, 1, 3, 1));
        expected.add(new LoadInstruction(0, 2, 0, 1));
        expected.add(new DeliverInstruction(0, 0, 0, 1));

        List<IInstruction> get = singleDroneStrategy.getInstructions();
        
        assertEquals(expected, get);
    }
    
    @Test
    public void randomContext() throws OverLoadException, ProductNotFoundException, NonValidCoordinatesException {
    	List<Product> products = Arrays.asList(
                new Product(100, 0),
                new Product(120, 1),
                new Product(90, 2),
    			new Product(10, 3));

    	Order order = new Order();
        order.addProduct(products.get(0), 2);
        Order order1 = new Order();
        order1.addProduct(products.get(1), 1);
        order1.addProduct(products.get(2), 1);
        order1.addProduct(products.get(3), 1);

        Context context = new Context.ContextBuilder(4, 4, 3, 25, 150)
                .addProducts(products)
                .addWarehouse(new Coordinates(0, 0), 2, 1, 1, 0)
                .addWarehouse(new Coordinates(1, 3), 0, 0, 0, 1)
                .addDeliveryPoint(new Coordinates(1, 1), order)
                .addDeliveryPoint(new Coordinates(0, 3), order1)
                .build();


        singleDroneStrategy = new SingleDroneStrategy(context);
        
        List<IInstruction> expected = new ArrayList<IInstruction>();
        expected.add(new LoadInstruction(0, 0, 0, 1));
        expected.add(new DeliverInstruction(0, 0, 0, 1));
        expected.add(new LoadInstruction(0, 0, 0, 1));
        expected.add(new DeliverInstruction(0, 0, 0, 1));
        expected.add(new LoadInstruction(0, 0, 1, 1));
        expected.add(new DeliverInstruction(0, 1, 1, 1));
        expected.add(new LoadInstruction(0, 1, 3, 1));
        expected.add(new DeliverInstruction(0, 1, 3, 1));
        expected.add(new LoadInstruction(0, 0, 2, 1));
        expected.add(new DeliverInstruction(0, 1, 2, 1));
        
        List<IInstruction> get = singleDroneStrategy.getInstructions();
        
        assertEquals(expected, get);
    }


}