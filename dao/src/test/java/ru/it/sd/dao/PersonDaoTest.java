package ru.it.sd.dao;

import ru.it.sd.dao.utils.FilterUtils;
import ru.it.sd.model.PagingRange;
import ru.it.sd.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.SortingInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @author quadrix
 * @since 05.05.2017
 */
@Sql("PersonDaoTest.sql")
public class PersonDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(PersonDaoTest.class);

	@Autowired
	private PersonDao dao;

	@Test
	private void testRead(){
		Person person = dao.read(1L);
		assertEquals(person.getId().longValue(), 1L);
		assertEquals(person.getFolder().getId().longValue(), 12345678L);
	}
	@Test
	private void testList() {
		List<Person> list = dao.list(null);
		assertEquals(list.size(), 7);
		LOG.debug(list.toString());
	}

	@Test
	private void testPaging() {
		Map<String, String> filter = new HashMap<>();
		filter.put(PagingRange.PAGING_PARAM_NAME, new PagingRange(1, 2).toString());

		List<Person> list = dao.list(filter);
		assertEquals(list.size(), 2);
		assertEquals(list.get(0).getId(), Long.valueOf(1));
		assertEquals(list.get(1).getId(), Long.valueOf(2));

		filter.put(PagingRange.PAGING_PARAM_NAME, new PagingRange(2, 4).toString());
		list = dao.list(filter);
		assertEquals(list.size(), 3);
		assertEquals(list.get(0).getId(), Long.valueOf(2));
		assertEquals(list.get(1).getId(), Long.valueOf(3));
		assertEquals(list.get(2).getId(), Long.valueOf(4));
	}

	@Test
	private void testSort() {
		Map<String, String> filter = new HashMap<>();
		filter.put(SortingInfo.SORTING_PARAM_NAME, "id-desc");

		List<Person> list = dao.list(filter);
		assertEquals(list.size(),7);
		assertEquals(list.get(0).getId(), Long.valueOf(7));
		assertEquals(list.get(1).getId(), Long.valueOf(6));
	}
	@Test
	private void testWorkgroupFilter(){
		Map<String, String> filter = new HashMap<>();
		filter.put("workgroupId", "20001");
		List<Person> list = dao.list(filter);
		assertEquals(list.size(), 2);

		filter.clear();
		filter.put(FilterUtils.FULLTEXT_FILTER_NAME, "ич");
		list = dao.list(filter);
		assertEquals(list.size(), 5);
	}

	@Test
    private void testFullnameLike(){
        Map<String, String> filter = new HashMap<>();
        filter.put("fullname_like", "ич");
        List<Person> list = dao.list(filter);
        assertEquals(list.size(), 2);
    }

}