package fr.unice.polytech.si3.dda;


import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.parser.OrderView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

//		if (args.length == 2) {
//			try {
//				Context ctx = new ContextParser(args[0]).parse();
//				List<Instruction> instructionsList = new ScheduleParser(args[1]).parse();
//				Visualiser visualiser = new Visualiser(ctx, instructionsList);
//				visualiser.display();
//			} catch (Exception e) {
//			}
//		}

		if (args.length == 2){
			File file = new File("../AmIDone_visualiser/log.json");
			try {
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
				bufferedWriter.write(new OrderView(args[0], args[1]).toJson());
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
