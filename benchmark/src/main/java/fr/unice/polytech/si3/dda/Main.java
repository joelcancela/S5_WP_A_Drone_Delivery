package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.metrics.CountDrone;
import fr.unice.polytech.si3.dda.metrics.InitScoring;

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
        try {
            ContextParser contextParser = new ContextParser(args[0]);
            ScheduleParser scheduleParser = new ScheduleParser(args[1]);
            InitScoring initScoring = new InitScoring(contextParser, scheduleParser);
            CountDrone countDrone = new CountDrone(scheduleParser);

            System.out.println("Score : " + initScoring.compute());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
