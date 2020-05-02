package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import model.Henkilo;
import model.dao.Dao;

@TestMethodOrder(OrderAnnotation.class)
class JUnit_testaa_asiakkaat {

	@Test
	@Order(3)
	void testListaaKaikki() {
		Dao dao = new Dao();
		ArrayList<Henkilo> henkilot = dao.listaaKaikki();
		assertEquals(2 ,henkilot.size());
	}

	@Test
	@Order(4)
	void testListaaKaikkiString() {
		Dao dao = new Dao();
		ArrayList<Henkilo> henkilot = dao.listaaKaikki("Janne");
		assertEquals(1 ,henkilot.size());
	}
	
	@Test
	@Order(1)
	void testPoistaAsiakaat() {
		Dao dao = new Dao();
		dao.poistaKaikki("oikein");
		ArrayList<Henkilo> henkilot = dao.listaaKaikki();
		assertEquals(0 ,henkilot.size());
	}

	@Test
	@Order(2)
	void testLisaaAsiakas() {
		Dao dao = new Dao();
		Henkilo henkilo = new Henkilo("Janne", "Hellsten", "test@email.com", "04511223344");
		Henkilo henkilo1 = new Henkilo("Pekka", "Kivinen", "testaus@email.com", "04511223344");
		assertEquals(true, dao.lisaaAsiakas(henkilo));	
		assertEquals(true, dao.lisaaAsiakas(henkilo1));	
	}

	@Test
	@Order(7)
	void testPoistaAsiakas() {
		Dao dao = new Dao();
		dao.poistaAsiakas("onnistunut@testi.com");
		ArrayList<Henkilo> henkilot = dao.listaaKaikki();
		assertEquals(1 ,henkilot.size());
	}

	@Test
	@Order(5)
	void testEtsiAsiakas() {
		Dao dao = new Dao();
		Henkilo asiakas = dao.etsiAsiakas("testaus@email.com");
		assertEquals("Pekka", asiakas.getEtunimi());
	}

	@Test
	@Order(6)
	void testMuutaAsiakas() {
		Dao dao = new Dao();
		Henkilo muutettava = dao.etsiAsiakas("testaus@email.com");
		muutettava.setEtunimi("Joonas");
		muutettava.setSukunimi("Pekkanen");
		muutettava.setPuhelin("111222333");
		muutettava.setSposti("onnistunut@testi.com");
		dao.muutaAsiakas(muutettava, "testaus@email.com");
		assertEquals("Joonas", dao.etsiAsiakas("onnistunut@testi.com").getEtunimi());
		assertEquals("Pekkanen", dao.etsiAsiakas("onnistunut@testi.com").getSukunimi());
		assertEquals("111222333", dao.etsiAsiakas("onnistunut@testi.com").getPuhelin());
		assertEquals("onnistunut@testi.com", dao.etsiAsiakas("onnistunut@testi.com").getSposti());
				
	}

}
