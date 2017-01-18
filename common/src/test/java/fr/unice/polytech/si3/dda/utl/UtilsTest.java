package fr.unice.polytech.si3.dda.utl;

import fr.unice.polytech.si3.dda.util.Utils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for Utils
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class UtilsTest {
	@Test
	public void TestStringArrayToIntArrayEmptyArray(){
		String[] stringArray = {};
		int[] intArray = Utils.stringArrayToIntArray(stringArray);
		assertNotNull(intArray);
		assertEquals(0, intArray.length);
	}

	@Test
	public void TestStringArrayToIntArrayNormalArray(){
		String[] stringArray = {"2","124","0", "-1"};
		int[] intArray = Utils.stringArrayToIntArray(stringArray);
		assertNotNull(intArray);
		assertEquals(4, intArray.length);
		assertEquals(2,intArray[0]);
		assertEquals(124,intArray[1]);
		assertEquals(0,intArray[2]);
		assertEquals(-1,intArray[3]);
	}
}
