package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.scheduler.Scheduler;

/**
 * Class Main
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Main {
	/**
	 * Main entry of the program
	 *
	 * @param args the arguments of the program
	 *             here we only expect one argument being the name
	 *             of the file to use in the scheduler
	 */
	public static void main(String[] args) {

		if (args.length > 0) {
			try {
				Context ctx = new ContextParser(args[0]).parse();
				new Scheduler(ctx).schedule();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
