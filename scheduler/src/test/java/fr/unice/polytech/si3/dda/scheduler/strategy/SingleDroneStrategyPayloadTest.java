package fr.unice.polytech.si3.dda.scheduler.strategy;

import fr.unice.polytech.si3.dda.ContextParser;
import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.instruction.DeliverInstruction;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.instruction.LoadInstruction;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SingleDroneStrategyPayloadTest {
	private SingleDroneStrategyPayload singleDroneStrategyPayload;

	@Test
	public void essaiContextFromFileCompleteExample() throws Exception {
		File file = new File("../examples/contextFoncManyOrders.in");
		ContextParser p = new ContextParser(file.getAbsolutePath());
		Context ctx = p.parse();

		//The same output as MultipleMaxDronePayloadStrategy but with only drone 0
		List<Instruction> expected = new ArrayList<Instruction>();
		expected.add(new LoadInstruction(0, 0, 1, 2));
		expected.add(new LoadInstruction(0, 0, 0, 1));
		expected.add(new DeliverInstruction(0, 2, 1, 2));
		expected.add(new DeliverInstruction(0, 0, 0, 1));
		expected.add(new LoadInstruction(0, 2, 2, 1));
		expected.add(new DeliverInstruction(0, 1, 2, 1));
		expected.add(new LoadInstruction(0, 1, 1, 2));
		expected.add(new DeliverInstruction(0, 2, 1, 1));
		expected.add(new DeliverInstruction(0, 0, 1, 1));

		singleDroneStrategyPayload = new SingleDroneStrategyPayload(ctx);
		singleDroneStrategyPayload.calculateInstructions();
		List<Instruction> get = singleDroneStrategyPayload.getInstructions();

		assertEquals(expected, get);
	}
}
