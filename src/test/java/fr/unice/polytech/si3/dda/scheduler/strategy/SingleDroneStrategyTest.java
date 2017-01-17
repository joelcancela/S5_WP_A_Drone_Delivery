package fr.unice.polytech.si3.dda.scheduler.strategy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.scheduler.Fleet;
import fr.unice.polytech.si3.dda.util.Coordinates;

public class SingleDroneStrategyTest {
	private SingleDroneStrategy singleDroneStrategy;

    @Before
    public void setUp() throws Exception {
    	List<Product> products = new ArrayList<Product>();
    	products.add(new Product(50));
    	products.add(new Product(20));
    	products.add(new Product(12));
    	products.add(new Product(16));
    	
    	Order order1 = new Order();
    	order1.addProduct(new Product(50), 2);
    	order1.addProduct(new Product(12), 1);
    	order1.addProduct(new Product(16), 1);
    	
    	Order order2 = new Order();
    	order2.addProduct(new Product(16), 1);
    	
    	Context context = new Context.ContextBuilder(4,4,5,25,100)
                .addProducts(products)
                .addWarehouse(new Coordinates(0,0), 1, 2, 3, 4)
                .addDeliveryPoint(new Coordinates(1,1), order1)
                .addDeliveryPoint(new Coordinates(0,3), order2).build();
        Fleet fleet = new Fleet(context);
        
        singleDroneStrategy = new SingleDroneStrategy(context, fleet);
    }
    
    @Test
    public void test() throws OverLoadException{
    	singleDroneStrategy.getInstructions();
    }
	
}
