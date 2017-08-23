package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.ChangeHistoryLine;
import ru.it.sd.model.Workgroup;

import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Sql("ChangeHistoryLineDaoTest.sql")
public class ChangeHistoryLineDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ChangeHistoryLineDaoTest.class);

	@Autowired
	private ChangeHistoryLineDao dao;

	@Test
	private void testFindOne() {
		ChangeHistoryLine item = dao.read(1L);
		assertNotNull(item);
		assertEquals(item.getId().longValue(), 1L);
		assertEquals(item.getSubject(), "Перенесен крайний срок");
	}

	@Test
	private void testFindByFilter(){
		HashMap<String, String> stringsFilter = new HashMap<>();
		stringsFilter.put("entity", "111");
		List<ChangeHistoryLine> items = dao.list(stringsFilter);
		assertEquals(items.size(), 2);

		stringsFilter = new HashMap<>();
		stringsFilter.put("entity", "222");
		items = dao.list(stringsFilter);
		assertEquals(items.size(), 1);
		assertEquals(items.get(0).getSubject(), "Закрытие заявки");
	}
}