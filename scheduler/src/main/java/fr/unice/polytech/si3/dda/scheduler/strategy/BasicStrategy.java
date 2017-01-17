package fr.unice.polytech.si3.dda.scheduler.strategy;

import fr.unice.polytech.si3.dda.instruction.IInstruction;
import fr.unice.polytech.si3.dda.instruction.WaitInstruction;
import fr.unice.polytech.si3.dda.scheduler.Context;

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
     * Return the list of instruction.
     *
     * @return the list of instruction.
     */
    @Override
    public List<IInstruction> getInstructions() {
        List<IInstruction> list = new ArrayList<>();

        for (int i = 0; i < context.getMaxDrones(); i++) {
            list.add(new WaitInstruction(i, context.getTurns()));
        }

        return list;
    }
}
