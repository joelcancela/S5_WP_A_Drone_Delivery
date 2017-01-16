package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.exception.NonValidCoordinatesException;
import fr.unice.polytech.si3.dda.scheduler.Scheduler;

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

		if(args.length>0){
			try {
				Context ctx = new Parser(args[0]).parse();
				new Scheduler(ctx).schedule();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
