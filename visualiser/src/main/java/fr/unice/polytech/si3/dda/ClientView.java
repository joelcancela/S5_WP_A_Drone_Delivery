package fr.unice.polytech.si3.dda;

import com.sun.scenario.effect.impl.prism.PrDrawable;
import fr.unice.polytech.si3.dda.instruction.IInstruction;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.scheduler.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientView extends View {

	Context ctx;
	List<IInstruction> instructions;
	private final int tickTime = 2000;

	public ClientView(Context context, List<IInstruction> instructionsList) {
		ctx = context;
		instructions = instructionsList;
	}

	@Override
	public void display() throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez votre numéro client: ");
		int clientNumber = sc.nextInt();
		System.out.println("\n");
		int turns = ctx.getTurns();
		Map<Product, Integer> initialProducts = new HashMap<>(ctx.getMap().getOrders().get(clientNumber).getProducts());
		clearScreen();


		for (IInstruction inst : instructions) {
			turns=turns-inst.execute(ctx);
			displayExecution(initialProducts,clientNumber,turns);
			Thread.sleep(tickTime);
			clearScreen();
		}

	}


	private void displayExecution(final Map initialOrderState, int clientNumber, int remainingTurns) {

		Order currentOrder = ctx.getMap().getOrders().get(clientNumber);
		int productsDelivered = (currentOrder.getNumberOfProducts()-currentOrder.getRemaining().size())/(currentOrder.getNumberOfProducts());
		int percentage = productsDelivered*100;
		System.out.println("N°client: " + clientNumber+"\n");
		System.out.println("#######"+"\n");
		System.out.println("Order: " +percentage+"% "+"("+remainingTurns+" turns remaining)"+"\n");
		// Percentage of the order
		// Review of the order and status
		// Remaining time

		int productType=0;
		for (Object p : initialOrderState.keySet()) {
			Product product = (Product) p;
			int remainingQuanitity = currentOrder.getQuantity(product);
			int initialQuantity = (Integer)initialOrderState.get(product);
			System.out.println("Item of type "+productType+": "+remainingQuanitity+"/"+initialQuantity);
			productType++;
		}
	}

}
