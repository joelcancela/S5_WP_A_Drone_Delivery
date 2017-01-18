package fr.unice.polytech.si3.dda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Drone;
import fr.unice.polytech.si3.dda.common.Fleet;
import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;

public class OperatorView extends View {

	private final int tickTime = 1000;
	//private int[] remainingTimeBeforeNextActions;

	public OperatorView(Context context, List<Instruction> instructionsList) {
		super(context, instructionsList);
	}

	@Override
	public void display(Scanner sc) throws InterruptedException, WrongIdException, OverLoadException, ProductNotFoundException {
		int turns = ctx.getTurns();
		// 1er index, n° du drone, 2e index n° de l'instruction
		List<List<Instruction>> execLine = new ArrayList<>();
		for (int i = 0; i < ctx.getMaxDrones(); i++) {
			execLine.add(new ArrayList<>());
		}
		for (Instruction inst : instructions) {
			execLine.get(inst.getDroneNumber()).add(inst);
		}
		int[] remainingTimeBeforeNextActions = new int[ctx.getMaxDrones()];
		for (int i = 0; i < remainingTimeBeforeNextActions.length; i++) {
			remainingTimeBeforeNextActions[i] = 0;
		}
		int[] indexInstDrone = new int[ctx.getMaxDrones()];
		for (int i = 0; i < remainingTimeBeforeNextActions.length; i++) {
			indexInstDrone[i] = 0;
		}
		boolean noMoreInstruction = false;
		while (turns >= 0 && !noMoreInstruction) {
			noMoreInstruction = true;
			for (int nbDrone = 0; nbDrone < remainingTimeBeforeNextActions.length; nbDrone++) {
				if (indexInstDrone[nbDrone] > execLine.get(nbDrone).size()) {
					// If there is no more instruction for this drone
					continue;
				}
				if (remainingTimeBeforeNextActions[nbDrone] == 0) {
					// If the instruction is finish
					indexInstDrone[nbDrone]++;
					if (indexInstDrone[nbDrone] <= execLine.get(nbDrone).size()) {
						execLine.get(nbDrone).get(indexInstDrone[nbDrone]-1).execute(ctx);
						if (indexInstDrone[nbDrone] != execLine.get(nbDrone).size()) {
							// If there is at least one more instruction
							remainingTimeBeforeNextActions[nbDrone] = execLine.get(nbDrone).get(indexInstDrone[nbDrone])
									.cost(ctx);
							noMoreInstruction = false;
						}
					}
				} else {
					// If the instruction is in progress
					noMoreInstruction = false;
					remainingTimeBeforeNextActions[nbDrone]--;
				}
			}
			displayExecution(remainingTimeBeforeNextActions);
			turns--;
			Thread.sleep(tickTime);
		}
	}

	public void displayExecution(int[] remainingTimeBeforeNextActions) {
		drawMap();
		System.out.println();
		drawDrones(remainingTimeBeforeNextActions);
		System.out.println();
		drawWarehouses();
		System.out.println();
		drawOrders();
	}

	private void drawOrders() {
		System.out.println("ORDERS");
		drawHorizontalLine(10);
		System.out.println();
		int i = 0;
		for (Map.Entry<Coordinates, DeliveryPoint> entry : ctx.getMap().getDeliveryPoints().entrySet()) {
			Order o = entry.getValue().getOrder();
			System.out.printf("Order %d: %s\n", i++, entry.getKey());
			for (int j = 0; j < ctx.getProducts().size(); j++) {
				Product p = ctx.getProducts().get(j);
				int quantity = o.getQuantity(p);
				if (quantity <= 0)
					continue;
				int remaining = (int) o.getRemaining().stream().filter(e -> e.equals(p)).count();
				System.out.printf("\tItem %d: %d/%d\n", j, quantity - remaining, quantity);
			}
		}
		System.out.println();
	}

	private void drawWarehouses() {
		System.out.println("WAREHOUSES INVENTORY");
		drawHorizontalLine(10);
		System.out.println();
		int i = 0;
		for (Map.Entry<Coordinates, Warehouse> entry : ctx.getMap().getWarehouses().entrySet()) {
			Warehouse w = entry.getValue();
			System.out.printf("Warehouse %d: %s\n", i++, entry.getKey());
			for (int j = 0; j < ctx.getProducts().size(); j++) {
				Product p = ctx.getProducts().get(j);
				System.out.printf("\tItem %d: %d\n", j, w.howManyProduct(p));
			}
		}
		System.out.println();
	}

	private void drawDrones(int[] remainingTimeBeforeNextActions) {
		Fleet fleet = ctx.getFleet();
		System.out.println("DRONES INVENTORY");
		drawHorizontalLine(10);
		System.out.println();
		for (int i = 0; i < fleet.getDronesNumber(); i++) {
			Drone d = fleet.getDrone(i);
			System.out.printf("Drone %d: %s\nTime until arrival: %d\n", i, d.getCoordinates().toString(), remainingTimeBeforeNextActions[i]);
			d.getLoadedProducts().stream().distinct()
					.forEach(p -> System.out.printf("\tItem %d: %d\n", ctx.getProducts().indexOf(p), d.getNumberOf(p)));
		}
		System.out.println();
	}

	private void drawMap() {
		Mapping map = ctx.getMap();
		System.out.println("MAP");
		System.out.print("+");
		drawHorizontalLine(map.getCols());
		System.out.println("+");
		// For the height of the map or the height of the caption
		for (int i = 0; i < map.getRows() || i < 3; i++) {
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

}
