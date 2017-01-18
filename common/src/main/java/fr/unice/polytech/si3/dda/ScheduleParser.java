package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.exception.EmptyFileException;
import fr.unice.polytech.si3.dda.exception.MalformedScheduleException;
import fr.unice.polytech.si3.dda.util.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class ScheduleParser
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class ScheduleParser {

    private BufferedReader br;

    /**
     * ScheduleParser constructor
     *
     * @param filename the filename to parse
     * @throws FileNotFoundException if the filename is incorrect
     */
    public ScheduleParser(String filename) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(filename));
    }

    public List<fr.unice.polytech.si3.dda.instruction.Instruction> parse() throws IOException, EmptyFileException, MalformedScheduleException {
        List<fr.unice.polytech.si3.dda.instruction.Instruction> instructions = new ArrayList<>();
        String line = br.readLine();
        if (line == null)
            throw new EmptyFileException();
        while (line != null) {
            String[] argss = line.split(" ");
            argss[1] = "0";
            int arguments[] = Utils.stringArrayToIntArray(argss);
            if (line.contains("L")) {
                instructions.add(new fr.unice.polytech.si3.dda.instruction.LoadInstruction(arguments[0], arguments[2], arguments[3], arguments[4]));
            } else if (line.contains("D")) {
                instructions.add(new fr.unice.polytech.si3.dda.instruction.DeliverInstruction(arguments[0], arguments[2], arguments[3], arguments[4]));
            } else if (line.contains("U")) {
                instructions.add(new fr.unice.polytech.si3.dda.instruction.UnloadInstruction(arguments[0], arguments[2], arguments[3], arguments[4]));
            } else if (line.contains("W")) {
                instructions.add(new fr.unice.polytech.si3.dda.instruction.WaitInstruction(arguments[0], arguments[2]));
            }
            line = br.readLine();
        }
        return instructions;
    }

}
