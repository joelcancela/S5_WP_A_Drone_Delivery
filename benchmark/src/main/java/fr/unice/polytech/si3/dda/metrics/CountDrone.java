package fr.unice.polytech.si3.dda.metrics;

import java.io.IOException;
import java.util.List;

import fr.unice.polytech.si3.dda.ScheduleParser;
import fr.unice.polytech.si3.dda.exception.EmptyFileException;
import fr.unice.polytech.si3.dda.exception.MalformedScheduleException;
import fr.unice.polytech.si3.dda.instruction.Instruction;

/**
 * Class CountDrone
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class CountDrone {
	List<Instruction> instructs;
	
	public CountDrone(ScheduleParser scheduleParser) throws Exception{
		instructs = scheduleParser.parse();
	}
	
}
