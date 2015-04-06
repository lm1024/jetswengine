package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import utils.Utils;

public class UtilsTests {

	@Test
	public void testValidARGB() {
		/* First, check all possible 8 digit hex strings */
		for (long i = -0xFFFFFFFF; i < 0xFFFFFFFF; i++) {
			if (i < 0) {
				assertFalse(Utils.validARGB("#" + Long.toHexString(i)));
				assertFalse(Utils.validARGB("#" + Long.toHexString(i).toUpperCase()));
			} else {
				assertTrue(Utils.validARGB("#" + Long.toHexString(i)));
				assertTrue(Utils.validARGB("#" + Long.toHexString(i).toUpperCase()));
			}
		}
		/* Then check common pitfalls and corner cases */
		assertFalse(Utils.validARGB(null));
		assertFalse(Utils.validARGB("RED"));
		assertFalse(Utils.validARGB("12345678"));
		assertFalse(Utils.validARGB("#ABCDEFGH"));
		assertFalse(Utils.validARGB(""));
		assertFalse(Utils.validARGB("#1234567"));
	}

}
