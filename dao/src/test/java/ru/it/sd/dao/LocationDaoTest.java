package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @author quadrix
 * @since 21.11.2017
 */
@Sql("LocationDaoTest.sql")
public class LocationDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(LocationDaoTest.class);

	@Autowired
	private LocationDao dao;

	@Test
	private void testRead() {
        Location location = dao.read(201L);
        assertEquals(location.getId().longValue(), 201L);
        assertEquals(location.getSearchcode(), "London");
    }

	@Test
	private void testList() {
		Map<String, String> filter = new HashMap<>();
		filter.put("searchcode_like","n");
		List<Location> list = dao.list(filter);
		assertEquals(list.size(), 2);
	}
}