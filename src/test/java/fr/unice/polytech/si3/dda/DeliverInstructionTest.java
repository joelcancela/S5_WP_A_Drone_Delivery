package fr.unice.polytech.si3.dda;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

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
		d.move(new Coordinates(1,1));
		inst = new DeliverInstruction(0, 0, 0, 1);
		assertEquals(2, inst.execute(ctx));
		assertEquals(new Coordinates(1,1), d.getCoordinates());
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
