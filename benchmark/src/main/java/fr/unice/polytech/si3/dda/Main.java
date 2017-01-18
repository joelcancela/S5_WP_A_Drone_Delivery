package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.benchmark.Benchmark;

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
		if (args.length == 2) {
			try {
				ContextParser contextParser = new ContextParser(args[0]);
				ScheduleParser scheduleParser = new ScheduleParser(args[1]);
				Benchmark kpis = new Benchmark(contextParser, scheduleParser);
				kpis.calculateKPIs();
				kpis.showDashboard();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
