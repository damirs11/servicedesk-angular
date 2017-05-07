package com.aplana.sd.model;

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
		assertEquals(p.getFIO(), "Пупкин В. А.");

		p.setFirstName(null);
		p.setLastName("Пупкин");
		p.setMiddleName("Абубабилович");
		assertEquals(p.getFIO(), "Пупкин А.");

		p.setFirstName("Вася");
		p.setLastName("Пупкин");
		p.setMiddleName(null);
		assertEquals(p.getFIO(), "Пупкин В.");

		p.setFirstName("Вася");
		p.setLastName(null);
		p.setMiddleName("Абубабилович");
		assertEquals(p.getFIO(), "- В. А.");
	}
}
