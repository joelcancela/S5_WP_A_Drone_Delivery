package fr.unice.polytech.si3.dda.instruction;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import fr.unice.polytech.si3.dda.ContextParser;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.instruction.DeliverInstruction;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.scheduler.Drone;
import fr.unice.polytech.si3.dda.util.Coordinates;

public class DeliverInstructionTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    String FILE_NAME = "new-file.txt";
    File file;
    DeliverInstruction inst;
    Context ctx;
	
	@Before
	public void setUp() throws Exception {
		file = new File(temp.getRoot(), FILE_NAME);
		BufferedWriter wrt = new BufferedWriter(new FileWriter(file));
		wrt.write("7 5 2 25 500\n"
				+ "3\n"
				+ "100 5 450\n"
				+ "1\n"
				+ "0 0\n"
				+ "5 1 0\n"
				+ "1\n"
				+ "1 1\n"
				+ "2\n"
				+ "2 0\n");
		wrt.close();
		ctx = new ContextParser(file.getAbsolutePath()).parse();
		
	}
	
	@Test
	public void testExecute() throws Exception{
		Drone d = ctx.getFleet().getDrone(0);
		d.load(new Product(100, 0));
		d.move(new Coordinates(1,0));
		inst = new DeliverInstruction(0, 0, 0, 1);
		assertEquals(2, inst.execute(ctx));
		assertEquals(new Coordinates(1,1), d.getCoordinates());
	}
	
	@Test(expected=ProductNotFoundException.class)
	public void testExecuteFail() throws Exception{
		Drone d = ctx.getFleet().getDrone(0);
		d.move(new Coordinates(1,0));
		inst = new DeliverInstruction(0, 0, 0, 1);
		assertEquals(2, inst.execute(ctx));
		assertEquals(new Coordinates(1,1), d.getCoordinates());
	}


	@Test
	public void testToString() {
		DeliverInstruction instruction1 = new DeliverInstruction(0,0,0,0);
		DeliverInstruction instruction2 = new DeliverInstruction(1,0,0,0);
		DeliverInstruction instruction3 = new DeliverInstruction(0,1,0,0);
		DeliverInstruction instruction4 = new DeliverInstruction(0,0,1,0);

		assertEquals("0 D 0 0 0", instruction1.toString());
		assertEquals("1 D 0 0 0", instruction2.toString());
		assertEquals("0 D 1 0 0", instruction3.toString());
		assertEquals("0 D 0 1 0", instruction4.toString());
	}

	@Test
	public void equals() throws Exception {
		DeliverInstruction instruction1 = new DeliverInstruction(0,0,0,0);
		DeliverInstruction instruction2 = new DeliverInstruction(1,0,0,0);
		DeliverInstruction instruction3 = new DeliverInstruction(0,1,0,0);
		DeliverInstruction instruction4 = new DeliverInstruction(0,0,1,0);
		DeliverInstruction instruction5 = new DeliverInstruction(0,0,0,1);

		assertEquals(instruction1, instruction1);
		assertNotEquals(instruction1, instruction2);
		assertNotEquals(instruction1, instruction3);
		assertNotEquals(instruction1, instruction4);
		assertNotEquals(instruction1, instruction5);
	}
}
