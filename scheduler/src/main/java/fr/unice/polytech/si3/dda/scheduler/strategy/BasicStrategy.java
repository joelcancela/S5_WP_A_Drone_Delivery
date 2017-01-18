package fr.unice.polytech.si3.dda.scheduler.strategy;


import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.instruction.WaitInstruction;

import java.util.ArrayList;
import java.util.List;

/**
 * Class BasicStrategy
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class BasicStrategy implements Strategy {
    private Context context;

    /**
     * Constructs a BasicStrategy from a context.
     *
     * @param context the context.
     */
    public BasicStrategy(Context context) {
        this.context = context;
    }

    /**
     * Return the list of fr.unice.polytech.si3.dda.exception.instruction.
     *
     * @return the list of fr.unice.polytech.si3.dda.exception.instruction.
     */
    @Override
    public List<Instruction> getInstructions() {
        List<Instruction> list = new ArrayList<>();

        for (int i = 0; i < context.getMaxDrones(); i++) {
            list.add(new WaitInstruction(i, context.getTurns()));
        }

        return list;
    }
}
