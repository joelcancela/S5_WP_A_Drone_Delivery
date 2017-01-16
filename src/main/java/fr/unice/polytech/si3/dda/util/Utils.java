package fr.unice.polytech.si3.dda.util;

public class Utils {

	public static int[] stringArrayToIntArray(String[] strings) {
		int[] res = new int[strings.length];
		for (int i = 0; i < res.length; i++)
			res[i] = Integer.parseInt(strings[i]);
		return res;
	}

}
