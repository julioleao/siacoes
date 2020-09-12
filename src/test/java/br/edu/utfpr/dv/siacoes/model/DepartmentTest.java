package br.edu.utfpr.dv.siacoes.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DepartmentTest {

	@Test
	void test() {
		Department dp = new Department();
		String initials = "UTFPR";
		dp.setInitials(initials);
		String res = dp.getInitials();
		
		assertEquals(initials, res, "passed");
		assertEquals("UFPR", res, "failed");
	}
}
