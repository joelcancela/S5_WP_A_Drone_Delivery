package fr.unice.polytech.si3.dda.scoring;

import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.instruction.DeliverInstruction;
import fr.unice.polytech.si3.dda.instruction.LoadInstruction;
import fr.unice.polytech.si3.dda.instruction.WaitInstruction;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by alexh on 18/01/2017.
 */
public class ScoringTest {


    @Test
    public void computeStrat() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(100, 0),
                new Product(5, 1),
                new Product(400, 2)
        );
        Order order = new Order();
        order.addProduct(products.get(1), 1);
        order.addProduct(products.get(0), 1);

        Context context = new Context.ContextBuilder(7, 5, 2, 25, 500)
                .addProducts(products)
                .addWarehouse(new Coordinates(0, 0), 5, 1, 0)
                .addDeliveryPoint(new Coordinates(1, 1), order)
                .build();


        Scoring scoring = new Scoring(Arrays.asList(
                new LoadInstruction(0, 0, 0, 1),
                new LoadInstruction(0, 0, 1, 1),
                new DeliverInstruction(0, 0, 0, 1),
                new DeliverInstruction(0, 0, 1, 1),
                new WaitInstruction(0, 3)),
                context);

        assertEquals(14, scoring.computeStrat());
    }



}