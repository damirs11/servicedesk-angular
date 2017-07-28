package ru.it.sd.web;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author quadrix
 * @since 03.11.2016 18:59
 */

public class AppSessionListenerTest {

	@Test
	private void getIntervalStringTest() {
		assertEquals(AppSessionListener.getIntervalString(1L), "1 мс ");
		assertEquals(AppSessionListener.getIntervalString(2456L), "2 секунд 456 мс ");
		assertEquals(AppSessionListener.getIntervalString(2857343256L), "793 часов 42 минут 23 секунд 256 мс ");
		assertEquals(AppSessionListener.getIntervalString(2000L), "2 секунд ");
		assertEquals(AppSessionListener.getIntervalString(2854823256L), "793 часов 23 секунд 256 мс ");
	}

	@Test
	private void getAuthInfoTest() {
		String springInfo = "asdasdasd asdf sdf [123as: safd]";
		assertEquals(AppSessionListener.getAuthInfo(springInfo), " - [123as: safd]");
		springInfo = "asdasdasd asdf sdf [XXX]assdf";
		assertEquals(AppSessionListener.getAuthInfo(springInfo), " - [XXX]");
	}
}