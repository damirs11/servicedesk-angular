package ru.it.sd.dao;

import ru.it.sd.model.Change;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * @author quadrix
 * @since 05.05.2017
 */
@Sql("ChangeDaoTest.sql")
public class ChangeDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ChangeDaoTest.class);

	@Autowired
	private ChangeDao dao;

	@Test
	private void testFindOne() {
		Change change = dao.read(55665L);
		assertNull(change);
		change = dao.read(111222L);
		assertNotNull(change);
		assertEquals(111222L, change.getId().longValue());
		assertEquals(112L, change.getNo().longValue());
	}
	@Test
	private void testByFilter() {
		HashMap<String, String> filter = new HashMap<>();
		filter.put("id","55665");
		List<Change> change = dao.list(filter);
		assertEquals(change.size(), 0);

		filter.clear();
		filter.put("id","111222;266633;331234");
		change = dao.list(filter);
		assertEquals(change.size(), 3);

		filter.clear();
		filter.put("no_between","10:16");
		change = dao.list(filter);
		assertEquals(change.size(), 3);

	}
}