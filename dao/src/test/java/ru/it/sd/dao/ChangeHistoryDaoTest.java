package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.ChangeHistory;

import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Sql("ChangeHistoryDaoTest.sql")
public class ChangeHistoryDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ChangeHistoryDaoTest.class);

	@Autowired
	private ChangeHistoryDao dao;

	@Test
	private void testFindOne() {
		ChangeHistory item = dao.read(1L);
		assertNotNull(item);
		assertEquals(item.getId().longValue(), 1L);
		assertEquals(item.getSubject(), "Перенесен крайний срок");
	}

	@Test
	private void testFindByFilter(){
		HashMap<String, String> stringsFilter = new HashMap<>();
		stringsFilter.put("entityId", "111");
		List<ChangeHistory> items = dao.list(stringsFilter);
		assertEquals(items.size(), 2);

		stringsFilter = new HashMap<>();
		stringsFilter.put("entityId", "222");
		items = dao.list(stringsFilter);
		assertEquals(items.size(), 1);
		assertEquals(items.get(0).getSubject(), "Закрытие заявки");
	}
}