package moviemad.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestRequestUtil_isStringEmpty {
	@Test
	public void shouldReturnFalseWhenNotEmpty() {
		assertFalse(RequestUtil.isStringEmpty("string"));
	}
	
	@Test
	public void shouldReturnFalseWhenNotEmptyWithTrailingAfter() {
		assertFalse(RequestUtil.isStringEmpty("string   "));
	}
	
	@Test
	public void shouldReturnFalseWhenNotEmptyWithTrailingBefore() {
		assertFalse(RequestUtil.isStringEmpty("   string"));
	}
	
	@Test
	public void shouldReturnTrueWhenEmpty() {
		assertTrue(RequestUtil.isStringEmpty(""));
	}

	@Test
	public void shouldReturnTrueWhenSpace() {
		assertTrue(RequestUtil.isStringEmpty(" "));
	}

	@Test
	public void shouldReturnFalseWhenNull() {
		assertFalse(RequestUtil.isStringEmpty(null));
	}

	@Test
	public void shouldReturnTrueWhenTab() {
		assertTrue(RequestUtil.isStringEmpty("\t"));
	}

	@Test
	public void shouldReturnTrueWhenSlashS() {
		assertTrue(RequestUtil.isStringEmpty("\s"));
	}

	@Test
	public void shouldReturnTrueWhenNewLine() {
		assertTrue(RequestUtil.isStringEmpty("\n"));
	}

	@Test
	public void shouldReturnTrueWhenEOF() {
		assertTrue(RequestUtil.isStringEmpty("\0"));
	}

}
