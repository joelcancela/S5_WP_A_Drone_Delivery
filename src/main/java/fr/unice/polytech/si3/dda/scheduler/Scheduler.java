package fr.unice.polytech.si3.dda.scheduler;

import fr.unice.polytech.si3.dda.Context;
import fr.unice.polytech.si3.dda.instruction.IInstruction;
import fr.unice.polytech.si3.dda.instruction.WaitInstruction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class Scheduler
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Scheduler {
	Context ctx;
	File scheduleOutFile;
	File mapOutFile;

	public Scheduler(Context context) {
		this.ctx = context;
		this.scheduleOutFile = new File("scheduler.out");
		this.mapOutFile = new File("map.csv");
	}

	public void schedule() throws IOException {
		FileWriter fw = new FileWriter(scheduleOutFile);
		IInstruction currentInstruction = new WaitInstruction(0,1);
		fw.write(currentInstruction.toString());
		fw.close();
		generateMapCsv();
	}

	public void generateMapCsv() throws IOException {
		FileWriter fw = new FileWriter(mapOutFile);
		fw.write(",W0,,");
		fw.write("\t");
		fw.close();
	}
}
