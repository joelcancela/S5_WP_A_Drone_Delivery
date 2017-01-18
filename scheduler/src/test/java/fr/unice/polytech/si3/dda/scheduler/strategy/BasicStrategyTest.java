package fr.unice.polytech.si3.dda.scheduler.strategy;


import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.instruction.WaitInstruction;
import fr.unice.polytech.si3.dda.util.Coordinates;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by alexh on 17/01/2017.
 */
public class BasicStrategyTest {
	private BasicStrategy strategy;
	private Context context;


	@Test
	public void getInstructions() throws Exception {

		context = new Context.ContextBuilder(4, 4, 3, 25, 150)
				.addProducts(new ArrayList<>())
				.addWarehouse(new Coordinates(0, 0))
				.build();

		strategy = new BasicStrategy(context);
		strategy.calculateInstructions();

		assertEquals(Arrays.asList(
				new WaitInstruction(0, 25),
				new WaitInstruction(1, 25),
				new WaitInstruction(2, 25)), strategy.getInstructions());
	}

}