package fr.unice.polytech.si3.dda;


import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.instruction.DeliverInstruction;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.util.Pair;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ClientView extends View {

	private final int tickTime = 2000;

	public ClientView(Context context, List<Instruction> instructionsList) {
		super(context, instructionsList);
	}

	@Override
	public void display(Scanner sc) throws InterruptedException, WrongIdException, OverLoadException, ProductNotFoundException {
		int clientNumber;
		do {
			System.out.println("Entrez votre numéro client: ");
			try {
				clientNumber = sc.nextInt();
			} catch (InputMismatchException e) {
				clientNumber = -1;
			}
			sc.nextLine();
		} while (clientNumber < 0 || clientNumber >= ctx.getMap().getOrders().size());
		System.out.println("\n");
		// Key: n° drone, Value: Pair <n° product, remaining time>
		List<Pair<Integer, Integer>> remainingMap = new ArrayList<>();
		// 1er index, n° du drone, 2e index n° de l'instruction
		List<List<Instruction>> execLine = new ArrayList<>();
		for (int i = 0; i < ctx.getMaxDrones(); i++) {
			execLine.add(new ArrayList<>());
		}
		for (Instruction inst : instructions) {
			execLine.get(inst.getDroneNumber()).add(inst);
		}
		int maxTurnDelivery = 0;
		for (List<Instruction> droneInst : execLine) {
			int turnBeforeDelivery = 0;
			for (Instruction inst : droneInst) {
				turnBeforeDelivery += inst.cost(ctx);
				if (inst instanceof DeliverInstruction) {
					DeliverInstruction di = (DeliverInstruction) inst;
					for (int i = 0; i < di.getNumberOfProducts(); i++) {
						if (di.getOrderNumber() == clientNumber) {
							remainingMap.add(new Pair<>(di.getProductType(), turnBeforeDelivery));
						}
					}
				}
			}
			maxTurnDelivery = Math.max(maxTurnDelivery, turnBeforeDelivery);
		}

		while (maxTurnDelivery >= 0) {
			displayExecution(clientNumber, maxTurnDelivery, remainingMap);
			maxTurnDelivery--;
			Thread.sleep(tickTime);
		}


	}


	private void displayExecution(int clientNumber, int remainingTurns, List<Pair<Integer, Integer>> remainingMap) {

		Order currentOrder = ctx.getMap().getOrders().get(clientNumber);
		float productsToDeliver = currentOrder.getNumberOfProducts();
		float productsLeft = remainingMap.stream().filter(pair -> pair.getSecond() > 0).count();
		int percentage = (int) ((productsToDeliver - productsLeft) / (productsToDeliver) * 100);
		drawHorizontalLine(10);
		System.out.print("\n");
		System.out.println("N° client: " + clientNumber);
		System.out.printf("Order: %d %% (%d turns remaining)%n%n", percentage, remainingTurns);

		for (Pair<Integer, Integer> pair : remainingMap) {
			System.out.printf("Item %d: ", pair.getFirst());
			if (pair.getSecond() > 0) {
				System.out.printf("(%d turns remaining)\n", pair.getSecond());
				pair.setSecond(pair.getSecond() - 1);
			} else {
				System.out.print("OK\n");
			}
		}

	}

}
