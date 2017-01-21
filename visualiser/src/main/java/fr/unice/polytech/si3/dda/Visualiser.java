package fr.unice.polytech.si3.dda;


import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.instruction.Instruction;

import java.util.List;
import java.util.Scanner;

/**
 * Class Visualiser
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Visualiser {

	private final int tickTime = 1000;

	Context ctx;
	List<Instruction> instructions;

	public Visualiser(Context context, List<Instruction> instructionsList) {
		ctx = context;
		instructions = instructionsList;
	}

	public void display() throws InterruptedException, WrongIdException, OverLoadException, ProductNotFoundException {
		View view = null;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Customer (C) or Operator (O) ?");
			switch (sc.nextLine()) {
				case "C":
					view = new ClientView(ctx, instructions);
					break;
				case "O":
					System.out.println("Manager");
					view = new OperatorView(ctx, instructions);
					break;
				default:
					System.out.println("Enter a valid answer.");
			}
		} while (view == null);
		view.display(sc);
		sc.close();
	}
}
