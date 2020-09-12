package br.edu.utfpr.dv.siacoes.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CampusTest {

	@Test
	void test() {
		Campus campus = new Campus();
		String address = "Av Paraná";
		campus.setAddress(address);
		String res = campus.getAddress();
		
		assertEquals(address, res, "passed");
		assertEquals("Rua Anchieta", res, "failed");
	}
}
