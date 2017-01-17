package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Context;

import java.util.*;

public class ClientView extends View {

	Context ctx;
	List<Instruction> instructions;
	private final int tickTime = 2000;

	public ClientView(Context context, List<Instruction> instructionsList) {
		ctx = context;
		instructions = instructionsList;
	}

	@Override
	public void display() throws InterruptedException, WrongIdException, OverLoadException, ProductNotFoundException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez votre numéro client: ");
		int clientNumber = sc.nextInt();
		System.out.println("\n");
		int turns = ctx.getTurns();
		Map<Product, Integer> initialProducts = new HashMap<>(ctx.getMap().getOrders().get(clientNumber).getProducts());
		clearScreen();


		for (Instruction inst : instructions) {
			turns = turns - inst.execute(ctx);
			displayExecution(initialProducts, clientNumber, turns);
			Thread.sleep(tickTime);
			clearScreen();
		}

		sc.close();

	}


	private void displayExecution(final Map<Product, Integer> initialOrderState, int clientNumber, int remainingTurns) {

		Order currentOrder = ctx.getMap().getOrders().get(clientNumber);
		float productsToDeliver = currentOrder.getNumberOfProducts();
		float productsLeft = currentOrder.getRemaining().size();
		int percentage = (int) ((productsToDeliver - productsLeft) / (productsToDeliver) * 100);
		System.out.println("####### \n");
		System.out.println("N°client: " + clientNumber);
		System.out.println("Order: " + percentage + "% " + "(" + remainingTurns + " turns remaining)" + "\n");//TODO Recalc turns
		// Percentage of the order
		// Review of the order and status
		// Remaining time

		for (Product p : initialOrderState.keySet()) {
			int initialQuantity = initialOrderState.get(p);
			int remainingQuantity = Collections.frequency(currentOrder.getRemaining(), p);

			for (int i = 0; i < initialQuantity; i++) {
				System.out.print("Item of type " + p.getId() + ": ");
				if (remainingQuantity != 0) {
					System.out.print("(" + remainingTurns + " turns remaining) \n");//TODO Recalc turns
					remainingQuantity--;
				} else {
					System.out.print("OK \n");
				}
			}
		}
	}

}
