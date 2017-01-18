package fr.unice.polytech.si3.dda.benchmark;

import fr.unice.polytech.si3.dda.ContextParser;
import fr.unice.polytech.si3.dda.ScheduleParser;
import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Benchmark
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Benchmark {

	private List<Instruction> strategy;
	private Context context;
	private int score;
	private int dronesUsed;
	private int dronesNumber;
	private int ordersNumber;
	private int ordersCompleted;
	private int ordersStarted;

	public Benchmark(ContextParser contextParser, ScheduleParser scheduleParser) throws Exception {
		context = contextParser.parse();
		strategy = scheduleParser.parse();
	}

	public Benchmark(List<Instruction> instructions, Context ctx) {
		strategy = instructions;
		context = ctx;
	}

	/**
	 * Calculate all KPIs for the current strategy
	 */
	public void calculateKPIs() throws Exception {
		//Score benchmark
		score = calculateScore();

		//Drones benchmark
		calculateDronesKPIs();

		//Orders benchmark
		calculateOrdersKPIs();

	}

	private void calculateOrdersKPIs() throws Exception {
		Context ordersCtx = new Context(context);
		for (Instruction i1 : strategy) {
			i1.execute(ordersCtx);
		}
		List<Order> orders = ordersCtx.getMap().getOrders();
		ordersNumber = orders.size();
		for(Order order: orders){
			if(order.hasStarted()){
				ordersStarted++;
			}
			if(order.isCompleted()){
				ordersCompleted++;
			}
		}
	}

	public int calculateScore() throws Exception {
		Context scoreCtx = new Context(context);
		int currentScore = 0;
		for (Instruction i1 : strategy) {
			currentScore += 1 + i1.execute(scoreCtx);
		}
		return currentScore;
	}

	private void calculateDronesKPIs() {
		Context dronesCtx = new Context(context);
		dronesNumber = dronesCtx.getMaxDrones();
		int[] array = new int[dronesNumber];
		for (Instruction i2 : strategy) {
			array[i2.getDroneNumber()]++;
		}
		dronesUsed=0;
		for (int droneUses : array) {
			if (droneUses != 0) {
				dronesUsed++;
			}
		}
	}

	public void showDashboard() {
		System.out.println("Benchmark Dashboard");
		System.out.println("#######Context#######");
		System.out.println("Drones number: "+dronesNumber);
		int rows = context.getMap().getRows();
		int cols = context.getMap().getCols();
		int mapSize = rows*cols;
		System.out.println("Field dimension: "+mapSize+" ("+rows+"*"+cols+")");
		drawMap();
		System.out.println("\n#######KPIs#######");
		System.out.println("Score: " + score);
		int dronesUsedPercentage = (int) (((float) dronesUsed / (float) dronesNumber) * 100);
		System.out.println("Drones used: " + dronesUsedPercentage + "% "+"("+dronesUsed+"/"+dronesNumber+")");
		System.out.println("Number of orders: "+ordersNumber);
		int ordersStartedPercentage = (int) (((float) ordersStarted / (float) ordersNumber) * 100);
		int ordersCompletedPercentage = (int) (((float) ordersCompleted / (float) ordersNumber) * 100);
		System.out.println("\t started: "+ ordersStartedPercentage + "% "+"("+ordersStarted+"/"+ordersNumber+")");
		System.out.println("\t completed: "+ ordersCompletedPercentage + "% "+"("+ordersCompleted+"/"+ordersNumber+")");
	}

	private void drawMap() {
		Mapping map = context.getMap();
		System.out.print("+");
		drawHorizontalLine(map.getCols());
		System.out.println("+");
		// For the height of the map or the height of the caption
		for (int i = 0; i < map.getRows() || i < 4; i++) {
			// For the width of the map
			System.out.print("|");
			for (int j = 0; j < map.getCols(); j++) {
				if (i < map.getRows()) {
					// If the map is big enough
					Coordinates coor = new Coordinates(i, j);
					if (map.getDeliveryPoints().containsKey(coor)) {
						System.out.print("O");
					} else if (map.getWarehouses().containsKey(coor)) {
						System.out.print("W");
					} else {
						System.out.print(" ");
					}
				} else {
					// Else we complete with spaces
					System.out.print(" ");
				}
			}
			System.out.print("|");
			drawCaption(i);
		}
		System.out.print("+");
		drawHorizontalLine(map.getCols());
		System.out.println("+");
	}

	private void drawCaption(int i) {
		switch (i) {
			case 0:
				System.out.println(" Caption:");
				break;
			case 1:
				System.out.println(" W: Warehouse");
				break;
			case 2:
				System.out.println(" O: Order");
				break;
			default:
				System.out.println();
		}
	}

	private void drawHorizontalLine(int width) {
		for (int i = 0; i < width; i++)
			System.out.print("-");
	}
}