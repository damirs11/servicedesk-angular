package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.*;

@Sql(scripts = {"ConfigurationItemDaoTest.sql", "PersonDaoTest.sql"})
public class ConfigurationItemTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationItemTest.class);

	@Autowired
	private ConfigurationItemDao dao;

	@Test
	private void testRead() {
		ConfigurationItem item = dao.read(20001L);
		assertNotNull(item);
		assertEquals(item.getId().longValue(), 20001L);
		assertEquals(item.getNo().longValue(), 2001L);
		assertEquals(item.getPrice(),8.50);
		LOG.info(item.toString());
	}

	@Test
	private void testFindByFilter(){
		HashMap<String, String> stringsFilter = new HashMap<>();
		stringsFilter.put("searchCode_like","CIT");
		stringsFilter.put("name_like","item");

		HashMap<String, String> ownerFilter = new HashMap<>();
		ownerFilter.put("owner","1");

		HashMap<String, String> numberFilter = new HashMap<>();
		numberFilter.put("no_between","2001:2002");

		HashMap<String, String> dateFilter = new HashMap<>();
		Long date1 = Timestamp.valueOf("2013-08-08 00:00:00").getTime();
		Long date2 = Timestamp.valueOf("2013-08-08 14:00:00").getTime();
		dateFilter.put("purchaseDate_between", date1.toString()+":"+date2.toString());

		List<ConfigurationItem> items = dao.list(stringsFilter);
		assertEquals(items.size(), 2);
		assertEquals(items.get(0).getId().longValue(), 20001L);
		System.out.println(items);
		items = dao.list(ownerFilter);
		assertEquals(items.size(),2);
		assertEquals(items.get(0).getOwner().getId().longValue(), 1);
		assertEquals(items.get(1).getOwner().getId().longValue(), 1);
        items = dao.list(numberFilter);

		assertEquals(items.size(),2);


		items = dao.list(dateFilter);
        assertEquals(items.size(),1);
	}
}