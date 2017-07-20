package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.it.sd.model.TestModel;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author quadrix
 * @since 05.05.2017
 */
public class FilterUtilsTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(FilterUtilsTest.class);

	@Test
	private void testEquals() {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

		HashMap<String, String> filter = new HashMap<>();
		filter.put("testInteger", "1234");
		filter.put("testint", "-1234");
		filter.put("testLong", "123456789");
		filter.put("testlong", "123456789");
		filter.put("testBoolean", "true");
		filter.put("testboolean", "false");
		filter.put("testDouble", "1.45");
		filter.put("testdouble", "0.45");
		filter.put("testString", "testetestest");
		filter.put("testDate", "1045645465000");

		FilterUtils.createFilter(sql, mapSqlParameterSource, filter, TestModel.class);
		Assert.assertEquals(mapSqlParameterSource.getValue("testInteger"), 1234);
		Assert.assertEquals(mapSqlParameterSource.getValue("testint"), -1234);
		Assert.assertEquals(mapSqlParameterSource.getValue("testLong"), 123456789L);
		Assert.assertEquals(mapSqlParameterSource.getValue("testlong"), 123456789L);
		Assert.assertEquals(mapSqlParameterSource.getValue("testBoolean"), true);
		Assert.assertEquals(mapSqlParameterSource.getValue("testboolean"), false);
		Assert.assertEquals(mapSqlParameterSource.getValue("testDouble"), 1.45);
		Assert.assertEquals(mapSqlParameterSource.getValue("testdouble"), 0.45);
		Assert.assertEquals(mapSqlParameterSource.getValue("testString"), "testetestest");
		Assert.assertEquals(mapSqlParameterSource.getValue("testDate"), new Timestamp(1045645465000L));
	}

	@Test
	private void testOtherModels() {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

		HashMap<String, String> filter = new HashMap<>();
		filter.put("testOrganization", "1234");
		filter.put("testFolder", "12345");
		filter.put("testPerson", "123456");

		FilterUtils.createFilter(sql, mapSqlParameterSource, filter, TestModel.class);
		Assert.assertEquals(mapSqlParameterSource.getValue("testOrganization"), 1234L);
		Assert.assertEquals(mapSqlParameterSource.getValue("testFolder"), 12345L);
		Assert.assertEquals(mapSqlParameterSource.getValue("testPerson"), 123456L);
	}

	@Test
	private void testList() {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

		Map<String, String> filter = new HashMap<>();
		filter.put("testdouble", "1.5;1.6;1.7");

		FilterUtils.createFilter(sql, mapSqlParameterSource, filter, TestModel.class);
		Assert.assertEquals(mapSqlParameterSource.getValue("testdouble0"), 1.5);
		Assert.assertEquals(mapSqlParameterSource.getValue("testdouble1"), 1.6);
		Assert.assertEquals(mapSqlParameterSource.getValue("testdouble2"), 1.7);
	}

	@Test
	private void testBetween() {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

		Map<String, String> filter = new HashMap<>();
		filter.put("testLong_between", "111111:222222");

		FilterUtils.createFilter(sql, mapSqlParameterSource, filter, TestModel.class);
		Assert.assertEquals(mapSqlParameterSource.getValue("testLong_from"), 111111L);
		Assert.assertEquals(mapSqlParameterSource.getValue("testLong_to"), 222222L);
	}

	@Test
	private void testAfter() {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

		HashMap<String, String> filter = new HashMap<>();
		filter.put("testDate_after", "1045645465000");

		FilterUtils.createFilter(sql, mapSqlParameterSource, filter, TestModel.class);
		Assert.assertEquals(mapSqlParameterSource.getValue("testDate_after"), new Timestamp(1045645465000L));
	}

	@Test
	private void testBefore() {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

		Map<String, String> filter = new HashMap<>();
		filter.put("testDate_before", "1045645465000");

		FilterUtils.createFilter(sql, mapSqlParameterSource, filter, TestModel.class);
		Assert.assertEquals(mapSqlParameterSource.getValue("testDate_before"), new Timestamp(1045645465000L));
	}

	@Test
	private void testLike() {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

		HashMap<String, String> filter = new HashMap<>();
		filter.put("testString_like", "asd");

		FilterUtils.createFilter(sql, mapSqlParameterSource, filter, TestModel.class);
		Assert.assertEquals(mapSqlParameterSource.getValue("testString_like"), "asd");
	}
}