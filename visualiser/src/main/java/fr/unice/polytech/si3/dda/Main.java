package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.scheduler.Context;

import java.util.List;

/**
 * Class Main
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Main {
	public static void main(String[] args) {

		if (args.length == 2) {
			try {
				Context ctx = new ContextParser(args[0]).parse();
				List<Instruction> instructionsList = new ScheduleParser(args[1]).parse();
				Visualiser visualiser = new Visualiser(ctx,instructionsList);
				visualiser.display();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
