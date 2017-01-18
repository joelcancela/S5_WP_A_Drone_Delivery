package fr.unice.polytech.si3.dda;


import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.instruction.Instruction;

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
	List<Instruction> instructions;

	public Visualiser(Context context, List<Instruction> instructionsList) {
		ctx = context;
		instructions = instructionsList;
	}

	public void display() throws InterruptedException, WrongIdException, OverLoadException, ProductNotFoundException {
		ClientView clientView = new ClientView(ctx,instructions);
		clientView.display();
	}
}
