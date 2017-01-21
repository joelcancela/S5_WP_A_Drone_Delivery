package fr.unice.polytech.si3.dda;


import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.parser.OrderView;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
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
			File file = new File("log.json");
			try {
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
				bufferedWriter.write(new OrderView(args[0], args[1]).toJson());
				bufferedWriter.close();
				if (Desktop.isDesktopSupported())
					Desktop.getDesktop().open(new File("AmIDone_visualiser/index.html"));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
