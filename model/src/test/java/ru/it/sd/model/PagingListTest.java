package ru.it.sd.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author quadrix
 * @since 13.06.2017
 */
public class PagingListTest {

	private static final Logger logger = LoggerFactory.getLogger(PagingListTest.class);

	@Test
	private void pagingListTest() {
		PagingList<String> pl = new PagingList<>();
		pl.setTotal(5);
		pl.add("abc");
		pl.add("def");
		assertEquals(pl.getTotal(), 5);
		logger.debug(pl.toString());
	}
}
