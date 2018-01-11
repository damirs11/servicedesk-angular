package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.Change;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

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
	private void testRead() {
		Change change = dao.read(55665L);
		assertNull(change);
		change = dao.read(111222L);
		assertNotNull(change);
		assertEquals(111222L, change.getId().longValue());
		assertEquals(112L, change.getNo().longValue());
		assertEquals(20001L, change.getAssWorkgroup().getId().longValue());
		assertEquals(3095134296L, change.getClassification().getId().longValue());
        assertEquals(3095397040L, change.getCategory().getId().longValue());
	}

	@Test
	private void testByFilter() {
		Map<String, String> filter = new HashMap<>();
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

	@Test
	private void testPersonFilter() {
		Map<String, String> filter = new HashMap<>();
		filter.put("personId", "2");
		// Фильтрация по пользователю по умолчанию
		List<Change> change = dao.list(filter);
		assertEquals(change.size(), 3);

		filter.put("filter", "executor");
		change = dao.list(filter);
		assertEquals(change.size(), 1);

		filter.put("filter", "approver");
		change = dao.list(filter);
		assertEquals(change.size(), 2);

		filter.put("filter", "initiator");
		change = dao.list(filter);
		assertEquals(change.size(), 2);

		filter.put("filter", "manager");
		change = dao.list(filter);
		assertEquals(change.size(), 3);
	}

	@Test
	private void testWorkGroupFilter() {
		Map<String, String> filter = new HashMap<>();

		filter.put("filter", "group_20001");
		List<Change> change = dao.list(filter);
		assertEquals(change.size(), 1);

		filter.put("filter", "group_20000");
		change = dao.list(filter);
		assertEquals(change.size(), 1);

		filter.put("personId", "2");

		filter.put("filter", "group_20001");
		change = dao.list(filter);
		assertEquals(change.size(), 0);

		filter.put("filter", "group_20000");
		change = dao.list(filter);
		assertEquals(change.size(), 0);
	}
}