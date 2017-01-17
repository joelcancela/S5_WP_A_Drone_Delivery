package fr.unice.polytech.si3.dda.instruction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.util.Coordinates;

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
	public void testToString() {
		fail("Not yet implemented");
	}

}
