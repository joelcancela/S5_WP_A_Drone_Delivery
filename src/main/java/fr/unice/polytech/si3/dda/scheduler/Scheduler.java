package fr.unice.polytech.si3.dda.scheduler;

import fr.unice.polytech.si3.dda.Context;
import fr.unice.polytech.si3.dda.Mapping;
import fr.unice.polytech.si3.dda.instruction.IInstruction;
import fr.unice.polytech.si3.dda.instruction.WaitInstruction;
import fr.unice.polytech.si3.dda.poi.DeliveryPoint;
import fr.unice.polytech.si3.dda.poi.PointOfInterest;
import fr.unice.polytech.si3.dda.poi.Warehouse;
import fr.unice.polytech.si3.dda.util.Coordinates;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

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
		for (int i = 0; i < ctx.getMaxDrones(); i++) {
			fw.write(new WaitInstruction(i, 1).toString());
			fw.write("\n");
		}
		fw.close();
		generateMapCsv();
	}

	public void generateMapCsv() throws IOException {
		FileWriter fw = new FileWriter(mapOutFile);
		Mapping map = ctx.getMap();
		int warehouses = 0;
		int deliveryPoints = 0;

		for (int i = 0; i < map.getRows(); i++) {
			for (int j = 0; j < map.getCols() ; j++) {
				PointOfInterest poi = map.getPointOfInterest(new Coordinates(i,j));
				if(poi==null){
					fw.write(";");
				}
				if(poi instanceof Warehouse){
					fw.write("W"+(warehouses++)+";");
				}
				if(poi instanceof DeliveryPoint){
					fw.write("O"+(deliveryPoints++)+";");
				}
			}
			fw.write("\n");

		}
		fw.close();
	}
}
