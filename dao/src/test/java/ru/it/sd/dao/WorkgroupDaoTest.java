package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.dao.utils.FilterUtils;
import ru.it.sd.model.Workgroup;

import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Sql("WorkgroupDaoTest.sql")
public class WorkgroupDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(WorkgroupDaoTest.class);

	@Autowired
	private WorkgroupDao dao;

	@Test
	private void testFindOne() {
		Workgroup item = dao.read(20001L);
		assertNotNull(item);
		assertEquals(item.getId().longValue(), 20001L);
		assertEquals(item.getName(), "Группа поддержки БД");
	}

	@Test
	private void testFindByFilter(){
		HashMap<String, String> filter = new HashMap<>();
		filter.put("searchCode_like", "поддержки");
		filter.put("name_like", "Группа");

		List<Workgroup> items = dao.list(filter);
		assertEquals(items.size(), 8);

		filter.clear();
		filter.put("name_like", "SD");
		items = dao.list(filter);
		assertEquals(items.size(), 5);

		filter.clear();
		filter.put("parentId", "20004");
		items = dao.list(filter);
		assertEquals(items.size(), 3);

		filter.clear();
		filter.put("personId", "1");
		items = dao.list(filter);
		assertEquals(items.size(), 2);

		filter.clear();
		filter.put("personId", "2");
		items = dao.list(filter);
		assertEquals(items.size(), 1);

		filter.clear();
		filter.put(FilterUtils.FULLTEXT_FILTER_NAME, "O");
		items = dao.list(filter);
		assertEquals(items.size(), 2);
	}
}