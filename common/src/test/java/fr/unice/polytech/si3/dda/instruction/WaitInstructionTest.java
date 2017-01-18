package fr.unice.polytech.si3.dda.instruction;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class WaitInstructionTest {

	WaitInstruction inst;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testExecute() throws ProductNotFoundException, WrongIdException, OverLoadException {
		inst = new WaitInstruction(0, 7);
		assertEquals(7, inst.execute(null));
	}

	@Test
	public void testEquals() {
		WaitInstruction waitInstruction = new WaitInstruction(3, 25);
		WaitInstruction waitInstruction2 = new WaitInstruction(2, 25);
		WaitInstruction waitInstruction3 = new WaitInstruction(3, 15);

		assertEquals(waitInstruction, waitInstruction);
		assertNotEquals(waitInstruction, waitInstruction2);
		assertNotEquals(waitInstruction, waitInstruction3);
		assertNotEquals(waitInstruction2, waitInstruction3);
	}

	@Test
	public void testToString() {
		WaitInstruction waitInstruction = new WaitInstruction(3, 25);
		assertEquals("3 W 25", waitInstruction.toString());
	}

}
