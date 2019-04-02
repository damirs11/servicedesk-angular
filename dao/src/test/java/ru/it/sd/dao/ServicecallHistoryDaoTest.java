package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.ServicecallHistory;

import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Sql("ServicecallHistoryDaoTest.sql")
public class ServicecallHistoryDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ServicecallHistoryDaoTest.class);

	@Autowired
	private ServicecallHistoryDao dao;

	@Test
	private void testFindOne() {
		ServicecallHistory item = dao.read(1L);
		assertNotNull(item);
		assertEquals(item.getId().longValue(), 1L);
	}

	@Test
	private void testFindByFilter(){
		HashMap<String, String> stringsFilter = new HashMap<>();
		stringsFilter.put("entityId", "1");
		List<ServicecallHistory> items = dao.list(stringsFilter);
		assertEquals(items.size(), 2);

		stringsFilter = new HashMap<>();
		stringsFilter.put("entityId", "2");
		items = dao.list(stringsFilter);
		assertEquals(items.size(), 1);
	}
}