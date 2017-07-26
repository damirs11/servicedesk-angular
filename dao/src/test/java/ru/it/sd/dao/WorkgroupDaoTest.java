package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.ConfigurationItem;
import ru.it.sd.model.Workgroup;

import java.sql.Timestamp;
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
		HashMap<String, String> stringsFilter = new HashMap<>();
		stringsFilter.put("searchCode_like","поддержки");
		stringsFilter.put("name_like","Группа");

		List<Workgroup> items = dao.list(stringsFilter);
		assertEquals(items.size(), 4);

	}
}