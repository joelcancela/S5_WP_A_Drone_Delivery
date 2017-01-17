package fr.unice.polytech.si3.dda;

import java.util.LinkedList;
import java.util.List;

import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.scheduler.Drone;
import fr.unice.polytech.si3.dda.util.Coordinates;

public class OperatorView extends View {

	public OperatorView(Context context, List<Instruction> instructionsList) {
		super(context, instructionsList);
	}

	@Override
	public void display() {
		// Get drones positions
		List<Coordinates> dronePos = new LinkedList<>();
		for (int i = 0; i < ctx.getMaxDrones(); i++) {
			Drone d = ctx.getFleet().getDrone(i);
			dronePos.add(d.getCoordinates());
		}
		drawMap(dronePos);
		System.out.println();
		drawDrones();
	}

	private void drawDrones() {
		
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
		switch(i){
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
