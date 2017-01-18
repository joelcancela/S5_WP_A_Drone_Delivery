package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.instruction.Instruction;

import java.util.List;
import java.util.Scanner;

public abstract class View {

	Context ctx;
	List<Instruction> instructions;

	public View(Context context, List<Instruction> instructionsList) {
		ctx = context;
		instructions = instructionsList;
	}

	public abstract void display(Scanner sc) throws InterruptedException, WrongIdException, OverLoadException, ProductNotFoundException;

	protected void drawHorizontalLine(int width) {
		for (int i = 0; i < width; i++)
			System.out.print("-");
	}

}
