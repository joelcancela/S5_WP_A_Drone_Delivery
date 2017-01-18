package fr.unice.polytech.si3.dda;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.scheduler.Drone;
import fr.unice.polytech.si3.dda.scheduler.Fleet;
import fr.unice.polytech.si3.dda.util.Coordinates;

public class OperatorView extends View {

	public OperatorView(Context context, List<Instruction> instructionsList) {
		super(context, instructionsList);
	}

	@Override
	public void display() {
		// Get drones positions
		List<Coordinates> dronePos = new LinkedList<>();
		for (Drone d : ctx.getFleet().getDrones()) {
			dronePos.add(d.getCoordinates());
		}
		drawMap(dronePos);
		System.out.println();
		drawDrones();
		System.out.println();
		drawWarehouses();
		System.out.println();
		drawOrders();
	}

	private void drawOrders() {
		System.out.println("ORDERS");
		drawHorizontalLine(10);
		System.out.println();
		int i=0;
		for (Entry<Coordinates, DeliveryPoint> entry: ctx.getMap().getDeliveryPoints().entrySet()) {
			Order o = entry.getValue().getOrder();
			System.out.printf("Order %d: %s\n", i++, entry.getKey());
			for (int j=0; j < ctx.getProducts().size(); j++) {
				Product p = ctx.getProducts().get(j);
				int quantity = o.getQuantity(p);
				if (quantity <= 0)
					continue;
				int remaining = (int) o.getRemaining().stream().filter(e->e.equals(p)).count();
				System.out.printf("\tItem %d: %d/%d\n", j, remaining,o.getQuantity(p));
			}
		}
		System.out.println();
	}

	private void drawWarehouses() {
		System.out.println("WAREHOUSES INVENTORY");
		drawHorizontalLine(10);
		System.out.println();
		int i=0;
		for (Entry<Coordinates, Warehouse> entry: ctx.getMap().getWarehouses().entrySet()) {
			Warehouse w = entry.getValue();
			System.out.printf("Warehouse %d: %s\n", i++, entry.getKey());
			for (int j=0; j < ctx.getProducts().size(); j++) {
				Product p = ctx.getProducts().get(j);
				System.out.printf("\tItem %d: %d\n", j, w.howManyProduct(p));
			}
		}
		System.out.println();
	}

	private void drawDrones() {
		Fleet fleet = ctx.getFleet();
		System.out.println("DRONES INVENTORY");
		drawHorizontalLine(10);
		System.out.println();
		for (int i = 0; i < fleet.getDronesNumber(); i++) {
			Drone d = fleet.getDrone(i);
			System.out.printf("Drone %d: %s\n", i, d.getCoordinates().toString());
			d.getLoadedProducts().stream().distinct()
					.forEach(p -> System.out.printf("\tItem %d: %d\n", ctx.getProducts().indexOf(p), d.getNumberOf(p)));
		}
		System.out.println();
	}

	private void drawMap(List<Coordinates> dronePos) {
		Mapping map = ctx.getMap();
		System.out.println("MAP");
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
					} else if (dronePos.contains(coor)) {
						System.out.print("D");
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
		case 3:
			System.out.println(" D: Drone");
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
