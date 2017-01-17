package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.instruction.IInstruction;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Context;

import java.util.*;

public class ClientView extends View {

	Context ctx;
	List<IInstruction> instructions;
	private final int tickTime = 2000;

	public ClientView(Context context, List<IInstruction> instructionsList) {
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


		for (IInstruction inst : instructions) {
			turns = turns - inst.execute(ctx);
			displayExecution(initialProducts, clientNumber, turns);
			Thread.sleep(tickTime);
			clearScreen();
		}

	}


	private void displayExecution(final Map initialOrderState, int clientNumber, int remainingTurns) {

		Order currentOrder = ctx.getMap().getOrders().get(clientNumber);
		float productsToDeliver = currentOrder.getNumberOfProducts();
		float productsLeft = currentOrder.getRemaining().size();
		int percentage = (int)((productsToDeliver - productsLeft)/(productsToDeliver) * 100);
		System.out.println("N°client: " + clientNumber + "\n");
		System.out.println("#######" + "\n");
		System.out.println("Order: " + percentage + "% " + "(" + remainingTurns + " turns remaining)" + "\n");
		// Percentage of the order
		// Review of the order and status
		// Remaining time

		for (Object p : initialOrderState.keySet()) {
			Product product = (Product) p;
			int initialQuantity = (Integer) initialOrderState.get(product);
			int remainingQuantity = Collections.frequency(currentOrder.getRemaining(), product);

			System.out.println("Item of type " + product.getId() + ": ");
			if (remainingQuantity != 0) {
				System.out.println(remainingQuantity + "/" + initialQuantity);
			} else {
				System.out.println("OK");
			}
		}
	}

}
