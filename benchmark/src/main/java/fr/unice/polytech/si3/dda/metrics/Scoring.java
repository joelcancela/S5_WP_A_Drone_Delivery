package fr.unice.polytech.si3.dda.metrics;

import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.instruction.Instruction;

import java.util.List;

/**
 * Class Scoring
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Scoring {

    private List<Instruction> strategy;
    private Context context;

    public Scoring(List<Instruction> strategy, Context context) {
        this.strategy = strategy;
        this.context = context;
    }

    /**
     * Compute the strategy and return the cost of a strategy.
     *=
     * @return the cost of the strategy.
     * @throws WrongIdException
     * @throws OverLoadException
     * @throws ProductNotFoundException
     */
    public int computeStrat() throws WrongIdException, OverLoadException, ProductNotFoundException {
        int maxDrone = context.getMaxDrones();
        int scoreMax = 0;
        for (int i = 0; i < maxDrone; i++) {
            int score = 0;
            for (Instruction instruction : strategy) {
                if (instruction.getDroneNumber() == i)
                    score += 1 + instruction.execute(context);
            }
            if (score > scoreMax) scoreMax = score;
        }
        return scoreMax;
    }
}
