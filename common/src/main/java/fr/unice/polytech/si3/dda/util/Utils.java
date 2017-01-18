package fr.unice.polytech.si3.dda.util;

/**
 * Class Utils
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public final class Utils {

	private Utils() {
	}

	/**
	 * Convert a string array into a int array
	 *
	 * @param strings the string array
	 * @return a int array
	 */
	public static int[] stringArrayToIntArray(String[] strings) {
		int[] res = new int[strings.length];
		for (int i = 0; i < res.length; i++)
			res[i] = Integer.parseInt(strings[i]);
		return res;
	}

}
