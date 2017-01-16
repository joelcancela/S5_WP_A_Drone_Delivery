package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.exception.NonValidCoordinatesException;
import fr.unice.polytech.si3.dda.scheduler.Scheduler;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Main class
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Main {
	public static void main(String[] args) {
		OptionParser parser = new OptionParser("i:");
		OptionSet options = parser.parse(args);

		if(options.has("i")){
			try {
				Context ctx = new Parser((String) options.valueOf("i")).parse();
				new Scheduler(ctx).schedule();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
