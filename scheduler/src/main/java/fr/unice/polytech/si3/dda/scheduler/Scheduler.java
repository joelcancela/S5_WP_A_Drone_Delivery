package fr.unice.polytech.si3.dda.scheduler;

import fr.unice.polytech.si3.dda.exception.*;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.scheduler.strategy.BasicStrategy;
import fr.unice.polytech.si3.dda.scheduler.strategy.MultipleDroneStrategy;
import fr.unice.polytech.si3.dda.scheduler.strategy.SingleDroneStrategy;
import fr.unice.polytech.si3.dda.scheduler.strategy.Strategy;
import fr.unice.polytech.si3.dda.util.Coordinates;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Scheduler
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Scheduler {
	Context ctx;
	File scheduleOutFile;
	File mapOutFile;
	private boolean forceWait;

	/**
	 * Scheduler constructor
	 *
	 * @param context is the context to be used by the scheduler
	 */
	public Scheduler(Context context, boolean forceWait) throws MalformedContextException {
		if(context==null){
			throw new MalformedContextException();
		}
		this.ctx = context;
		this.scheduleOutFile = new File("scheduler.out");
		this.mapOutFile = new File("map.csv");
		this.forceWait = forceWait;
	}

	/**
	 * Launches algorithm and writes the instructions to the output file "scheduler.out"
	 *
	 * @throws IOException if you can't write on the output file
	 * @throws StrategyException 
	 */
	public void schedule() throws IOException, StrategyException {
		List<Strategy> strategies =  new ArrayList<>();
		if (forceWait) {
			strategies.add(new BasicStrategy(ctx));
		}
		else {
			strategies.add(new MultipleDroneStrategy(new Context(ctx)));
			strategies.add(new SingleDroneStrategy(new Context(ctx)));
		}
		int minCost = Integer.MAX_VALUE;
		Strategy bestStrategy = null;
		for(Strategy strategy : strategies){
		    List<Instruction> currentInstructions = strategy.getInstructions();

		    int cost = computeSrat(currentInstructions, new Context(ctx));
			
			if(cost<minCost){
				minCost = cost;
				bestStrategy = strategy;
			}
		}
		FileWriter fw = new FileWriter(scheduleOutFile);
		for(Instruction instruction: bestStrategy.getInstructions()){
			fw.write(instruction.toString());
			fw.write("\n");
		}
		fw.close();
		generateMapCsv();
	}

	/**
	 * Compoute the strategy and return the cost of a strategy.
	 *
	 * @param strategy the strategy to compute.
	 * @param context  the context for the strategy.
	 * @return the cost of the strategy.
	 * @throws WrongIdException
	 * @throws OverLoadException
	 * @throws ProductNotFoundException
	 */
	public static int computeSrat(List<Instruction> strategy, Context context) throws WrongIdException, OverLoadException, ProductNotFoundException {
		int maxDrone = context.getMaxDrones();
		int scoreMax = 0;
		for (int i = 0; i < maxDrone; i++) {
			int score = 0;
			for (Instruction instruction : strategy){
				if (instruction.getDroneNumber() == i)
					score += 1 + instruction.execute(context);
			}
			if (score > scoreMax) scoreMax = score;
		}
		return scoreMax;
	}

	/**
	 * Generate the current map to csv file
	 *
	 * @throws IOException if you can't write on the output file
	 */
	public void generateMapCsv() throws IOException {
		FileWriter fw = new FileWriter(mapOutFile);
		Mapping map = ctx.getMap();
		int warehouses = 0;
		int deliveryPoints = 0;

		for (int i = 0; i < map.getRows(); i++) {
			for (int j = 0; j < map.getCols(); j++) {
				Warehouse warehouse = map.getWarehouse(new Coordinates(i, j));
				DeliveryPoint deliveryPoint = map.getDeliveryPoint(new Coordinates(i,j));
				if (warehouse == null && deliveryPoint == null) {
					fw.write(";");
				}
				if (warehouse != null) {
					fw.write("W" + (warehouses++) + ";");
				}
				if (deliveryPoint != null) {
					fw.write("O" + (deliveryPoints++) + ";");
				}
			}
			fw.write("\n");

		}
		fw.close();
	}
}
