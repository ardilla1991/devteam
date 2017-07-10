package by.htp.devteam.util;

import static org.junit.Assert.*;

import org.junit.Test;

import by.htp.devteam.service.validation.Validator;

public class ValidatorTest {

	@Test
	public void testCheckBigDecimal() {
		assertEquals(true, Validator.checkBigDecimal("0.8"));
		assertEquals(true, Validator.checkBigDecimal("12.88"));
		assertEquals(false, Validator.checkBigDecimal("12.888"));
		assertEquals(false, Validator.checkBigDecimal("ghjh"));
		assertEquals(false, Validator.checkBigDecimal("rrr.rrr"));
		assertEquals(false, Validator.checkBigDecimal("0,89"));
		assertEquals(false, Validator.checkBigDecimal(".f"));
	}
	
	@Test
	public void testIsCorrectFileExtension() {
		String[] extensions = new String[] {
				   "a.rar", "a.zip","a.doc", "a.docx", "a.odt", "a.txt",
				   "..rar", "..zip","..doc", "..docx", "..odt", "..txt",
				   "a.RAR", "a.ZIP","a.DOC", "a.DOCX", "a.ODT", "a.TXT",
				   "a.RaR", "a.ZiP","a.DoC", "a.DocX", "a.OdT", "a.TxT",
				   "rar.rar", "zip.zip","doc.doc", "docx.docx", "odt.odt", "txt.txt"
		  	       };
		for ( String ext : extensions ) {
			assertEquals(true, Validator.isCorrectFileExtension(ext));
		}

		extensions = new String[] {
				   ".rar", ".zip",".doc",".docx", ".odt", ".txt",
				   " .rar", " .zip"," .doc"," .docx", " .odt", " .txt",
		           "a.png", "a.exe","a.","a.mp3",
				   "rar", "zip","png","bmp"
			       };
		for ( String ext : extensions ) {
			assertEquals(false, Validator.isCorrectFileExtension(ext));
		}
	}
	
}
