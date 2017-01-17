package fr.unice.polytech.si3.dda;

import java.util.List;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.scheduler.Context;

public abstract class View {
	
	Context ctx;
	List<Instruction> instructions;
	
	public View(Context context, List<Instruction> instructionsList) {
		ctx = context;
		instructions = instructionsList;
	}

	public abstract void display() throws InterruptedException, WrongIdException, OverLoadException, ProductNotFoundException;

	protected void clearScreen() {
		System.out.print("\033[" + "2J");
	}

}
