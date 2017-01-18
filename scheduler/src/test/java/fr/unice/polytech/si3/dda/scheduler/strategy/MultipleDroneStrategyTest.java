package fr.unice.polytech.si3.dda.scheduler.strategy;


import fr.unice.polytech.si3.dda.ContextParser;
import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Fleet;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.instruction.DeliverInstruction;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.instruction.LoadInstruction;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by alexh on 17/01/2017.
 */
public class MultipleDroneStrategyTest {
    private MultipleDroneStrategy strategy;
    private Context context;
    private Mapping mapping;
    private Fleet fleet;
    private List<Product> products;
    private Order order;
    private Order order1;


    @Before
    public void setUp() throws Exception {
        products = Arrays.asList(
                new Product(100, 0),
                new Product(120, 1),
                new Product(90, 2),
                new Product(10, 3));

        order = new Order();
        order.addProduct(products.get(0), 2);
        order1 = new Order();
        order1.addProduct(products.get(1), 1);
        order1.addProduct(products.get(2), 1);
        order1.addProduct(products.get(3), 1);

        context = new Context.ContextBuilder(4, 4, 3, 25, 150)
                .addProducts(products)
                .addWarehouse(new Coordinates(0, 0), 2, 1, 1, 0)
                .addWarehouse(new Coordinates(1, 3), 0, 0, 0, 1)
                .addDeliveryPoint(new Coordinates(1, 1), order)
                .addDeliveryPoint(new Coordinates(0, 3), order1)
                .build();
        mapping = context.getMap();
        fleet = context.getFleet();
        strategy = new MultipleDroneStrategy(context);

    }

    @Test
    public void getInstructions() throws Exception {
        List<Instruction> list = strategy.getInstructions();
        List<Instruction> expect = Arrays.asList(
                new LoadInstruction(0, 0, 0, 1),
                new DeliverInstruction(0, 0, 0, 1),
                new LoadInstruction(1, 0, 0, 1),
                new DeliverInstruction(1, 0, 0, 1),
                new LoadInstruction(2, 0, 1, 1),
                new DeliverInstruction(2, 1, 1, 1),
                new LoadInstruction(0, 0, 2, 1),
                new DeliverInstruction(0, 1, 2, 1),
                new LoadInstruction(1, 1, 3, 1),
                new DeliverInstruction(1, 1, 3, 1)
        );
        assertEquals(expect, list);
    }

    @Test
    public void deliverProduct() throws Exception {
        strategy.loadFromWarehouse(0, products.get(0), mapping.getWarehouse(0));
        assertTrue(order.getRemaining().contains(products.get(0)));
        assertTrue(!fleet.getDrone(0).isEmpty());
        int sizeBefore = order.getRemaining().size();
        strategy.deliverProduct(0, products.get(0), mapping.getDeliveryPoint(0));
        assertTrue(order.getRemaining().size() < sizeBefore);
        assertTrue(fleet.getDrone(0).isEmpty());

    }

    @Test
    public void loadFromWarehouse() throws Exception {
        strategy.loadFromWarehouse(0, products.get(0), mapping.getWarehouse(0));
        assertTrue(!fleet.getDrone(0).isEmpty());
    }

    @Test
    public void searchForItem() throws Exception {
        fleet.getDrone(0).move(new Coordinates(0, 0));
        strategy.searchForItem(0, products.get(3), mapping.getWarehouse(new Coordinates(0, 0)));
        assertEquals(new Coordinates(1, 3), fleet.getDrone(0).getCoordinates());
        assertTrue(!fleet.getDrone(0).isEmpty());
    }

    @Test(expected = ProductNotFoundException.class)
    public void loadProductError() throws Exception {
        strategy.loadFromWarehouse(0, new Product(12, 12), mapping.getWarehouse(0));
    }

    @Test
    public void testdemo() throws Exception {
        File file = new File("../examples/contextDemo_simple.in");
        ContextParser p = new ContextParser(file.getAbsolutePath());
        Context ctx = p.parse();

        MultipleDroneStrategy strategy = new MultipleDroneStrategy(ctx);
        List<Instruction> list = strategy.getInstructions();
        assertTrue(list.contains(new LoadInstruction(0,0,1,1)));
        assertTrue(list.contains(new DeliverInstruction(0,0,1,1)));
        assertTrue(list.contains(new LoadInstruction(1,0,0,1)));
        assertTrue(list.contains(new DeliverInstruction(1,0,0,1)));
    }

}