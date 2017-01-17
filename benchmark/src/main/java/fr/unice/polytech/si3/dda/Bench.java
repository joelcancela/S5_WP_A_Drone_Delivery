package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.scheduler.Context;

import java.util.List;

/**
 * Created by alexh on 17/01/2017.
 */
public class Bench {



    /**
     * Compoute the strategy and return the cost of a strategy.
     *
     * @param strategy the strategy to compute.
     * @param context  the context for the strategy.
     * @return the cost of the strategy.
     * @throws WrongIdException
     * @throws OverLoadException
     * @throws ProductNotFoundException
     */
    public static int computeSrat(List<Instruction> strategy, Context context) throws WrongIdException, OverLoadException, ProductNotFoundException {
        int maxDrone = context.getMaxDrones();
        int scoreMax = 0;
        for (int i = 0; i < maxDrone; i++) {
            int score = 0;
            for (Instruction instruction : strategy){
                if (instruction.getDroneNumber() == i)
                    score += 1 + instruction.execute(context);
            }
            if (score > scoreMax) scoreMax = score;
        }
        return scoreMax;
    }

}
