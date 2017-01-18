package fr.unice.polytech.si3.dda.scoring;

import fr.unice.polytech.si3.dda.ContextParser;
import fr.unice.polytech.si3.dda.ScheduleParser;
import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.exception.*;
import fr.unice.polytech.si3.dda.instruction.Instruction;

import java.io.IOException;
import java.util.List;

/**
 * Created by alexh on 18/01/2017.
 */
public class InitScoring {
    private ContextParser contextParser;
    private ScheduleParser scheduleParser;

    public InitScoring(ContextParser contextParser, ScheduleParser scheduleParser) {
        this.contextParser = contextParser;
        this.scheduleParser = scheduleParser;
    }

    public int compute() throws NonValidCoordinatesException, MalformedContextException, MalformedContextBodyException, EmptyFileException, IOException, MalformedScheduleException, WrongIdException, OverLoadException, ProductNotFoundException {
        Context context = contextParser.parse();
        List<Instruction> strat = scheduleParser.parse();

        Scoring scoring = new Scoring(strat, context);
        return scoring.computeStrat();


    }

}
