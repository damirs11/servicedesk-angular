package ru.it.sd.model;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author quadrix
 * @since 08.05.2017
 */
public class PersonTest {

	@Test
	private void testGetFIO() {
		Person p = new Person();

		p.setFirstName("Вася");
		p.setLastName("Пупкин");
		p.setMiddleName("Абубабилович");
		assertEquals(p.getShortName(), "Пупкин В. А.");

		p.setFirstName(null);
		p.setLastName("Пупкин");
		p.setMiddleName("Абубабилович");
		assertEquals(p.getShortName(), "Пупкин А.");

		p.setFirstName("Вася");
		p.setLastName("Пупкин");
		p.setMiddleName(null);
		assertEquals(p.getShortName(), "Пупкин В.");

		p.setFirstName("Вася");
		p.setLastName(null);
		p.setMiddleName("Абубабилович");
		assertEquals(p.getShortName(), "- В. А.");
	}
}
