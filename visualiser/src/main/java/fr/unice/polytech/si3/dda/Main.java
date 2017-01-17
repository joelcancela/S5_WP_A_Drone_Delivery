package fr.unice.polytech.si3.dda;

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

		if (args.length > 0) {
			try {
				System.out.println(new Parser(args[0]).parse().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
