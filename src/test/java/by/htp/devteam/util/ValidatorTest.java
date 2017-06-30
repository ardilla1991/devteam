package by.htp.devteam.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidatorTest {

	@Test
	public void testCheckBigDecimal() {
		assertEquals(true, Validator.checkBigDecimal("0.8"));
		assertEquals(true, Validator.checkBigDecimal("12.888"));
		assertEquals(false, Validator.checkBigDecimal("ghjh"));
		assertEquals(false, Validator.checkBigDecimal("rrr.rrr"));
		assertEquals(false, Validator.checkBigDecimal("0,89"));
		assertEquals(false, Validator.checkBigDecimal(".f"));
	}
	
	
}
