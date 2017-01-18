package fr.unice.polytech.si3.dda.scheduler.strategy;

import fr.unice.polytech.si3.dda.ContextParser;
import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.exception.GlobalException;
import fr.unice.polytech.si3.dda.instruction.DeliverInstruction;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.instruction.LoadInstruction;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MultipleMaxDronePayloadStrategyTest {
    private MultipleMaxDronePayloadStrategy multipleMaxDronePayloadStrategy;

    @Test
    public void finishAOrderAndMoveToNearestWarehouse() throws GlobalException {
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

        multipleMaxDronePayloadStrategy = new MultipleMaxDronePayloadStrategy(context);


        multipleMaxDronePayloadStrategy.calculateInstructions();
        List<Instruction> get = multipleMaxDronePayloadStrategy.getInstructions();
        System.out.println(get);
    }

    @Test
    public void essaiContextFromFileCompleteExample() throws Exception {
        File file = new File("../examples/contextFoncManyOrders.in");
        ContextParser p = new ContextParser(file.getAbsolutePath());
        Context ctx = p.parse();

        List<Instruction> expected = new ArrayList<Instruction>();
        expected.add(new LoadInstruction(0, 0, 1, 2));
        expected.add(new LoadInstruction(0, 0, 0, 1));
        expected.add(new DeliverInstruction(0, 2, 1, 2));
        expected.add(new DeliverInstruction(0, 0, 0, 1));
        expected.add(new LoadInstruction(1, 2, 2, 1));
        expected.add(new DeliverInstruction(1, 1, 2, 1));
        expected.add(new LoadInstruction(0, 1, 1, 2));
        expected.add(new DeliverInstruction(0, 2, 1, 1));
        expected.add(new DeliverInstruction(0, 0, 1, 1));

        multipleMaxDronePayloadStrategy = new MultipleMaxDronePayloadStrategy(ctx);
        multipleMaxDronePayloadStrategy.calculateInstructions();
        List<Instruction> get = multipleMaxDronePayloadStrategy.getInstructions();

        assertEquals(expected, get);
    }
}
