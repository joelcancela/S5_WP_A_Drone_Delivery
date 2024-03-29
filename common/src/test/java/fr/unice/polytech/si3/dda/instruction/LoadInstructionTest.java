package fr.unice.polytech.si3.dda.instruction;

import fr.unice.polytech.si3.dda.ContextParser;
import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Drone;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LoadInstructionTest {

	@Rule
	public TemporaryFolder temp = new TemporaryFolder();

	String FILE_NAME = "new-file.txt";
	File file;
	LoadInstruction inst;
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
	public void testExecute() throws Exception {
		Drone d = ctx.getFleet().getDrone(0);
		Warehouse w = ctx.getMap().getWarehouse(0);
		d.move(new Coordinates(1, 0));
		assertEquals(0, d.getLoadedProducts().size());
		assertEquals(5, w.howManyProduct(new Product(100, 0)));
		inst = new LoadInstruction(0, 0, 0, 1);
		assertEquals(2, inst.execute(ctx));
		assertEquals(new Coordinates(0, 0), d.getCoordinates());
		assertEquals(1, d.getLoadedProducts().size());
		assertEquals(4, w.howManyProduct(new Product(100, 0)));
	}

	@Test(expected = ProductNotFoundException.class)
	public void testExecuteFail() throws Exception {
		Drone d = ctx.getFleet().getDrone(0);
		Warehouse w = ctx.getMap().getWarehouse(0);
		d.move(new Coordinates(1, 0));
		inst = new LoadInstruction(0, 0, 2, 1);
		assertEquals(2, inst.execute(ctx));
		assertEquals(new Coordinates(0, 0), d.getCoordinates());
	}

	@Test
	public void testToString() {
		LoadInstruction loadInstruction = new LoadInstruction(0, 0, 0, 0);
		assertEquals("0 L 0 0 0", loadInstruction.toString());
	}

	@Test
	public void testEquals() {
		LoadInstruction loadInstruction = new LoadInstruction(0, 0, 0, 0);
		LoadInstruction loadInstruction1 = new LoadInstruction(1, 0, 0, 0);
		LoadInstruction loadInstruction2 = new LoadInstruction(0, 1, 0, 0);
		LoadInstruction loadInstruction3 = new LoadInstruction(0, 0, 1, 0);
		LoadInstruction loadInstruction4 = new LoadInstruction(0, 0, 0, 1);

		assertEquals(loadInstruction, loadInstruction);
		assertNotEquals(loadInstruction, loadInstruction1);
		assertNotEquals(loadInstruction, loadInstruction2);
		assertNotEquals(loadInstruction, loadInstruction3);
		assertNotEquals(loadInstruction, loadInstruction4);

	}

}
