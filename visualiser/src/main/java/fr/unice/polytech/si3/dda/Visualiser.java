package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.instruction.IInstruction;
import fr.unice.polytech.si3.dda.scheduler.Context;

import java.util.List;

/**
 * Class Visualiser
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Visualiser {
	Context ctx;
	List<IInstruction> instructions;

	public Visualiser(Context context, List<IInstruction> instructionsList) {
		ctx = context;
		instructions = instructionsList;
	}

	public void display() throws InterruptedException {
		ClientView clientView = new ClientView(ctx,instructions);
		clientView.display();
	}
}
