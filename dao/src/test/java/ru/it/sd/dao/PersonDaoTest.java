package ru.it.sd.dao;

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

import static org.testng.AssertJUnit.assertEquals;

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
	private void testList() {
		List<Person> list = dao.list(null);
		assertEquals(4, list.size());
		LOG.debug(list.toString());
	}

	@Test
	private void testPaging() {
		Map<String, String> filter = new HashMap<>();
		filter.put(PagingRange.PAGING_PARAM_NAME, new PagingRange(1, 2).toString());

		List<Person> list = dao.list(filter);
		assertEquals(2, list.size());
		assertEquals(Long.valueOf(1), list.get(0).getId());
		assertEquals(Long.valueOf(2), list.get(1).getId());

		filter.put(PagingRange.PAGING_PARAM_NAME, new PagingRange(2, 4).toString());
		list = dao.list(filter);
		assertEquals(3, list.size());
		assertEquals(Long.valueOf(2), list.get(0).getId());
		assertEquals(Long.valueOf(3), list.get(1).getId());
		assertEquals(Long.valueOf(4), list.get(2).getId());
	}

	@Test
	private void testSort() {
		Map<String, String> filter = new HashMap<>();
		filter.put(SortingInfo.SORTING_PARAM_NAME, "id-desc");

		List<Person> list = dao.list(filter);
		assertEquals(4, list.size());
		assertEquals(Long.valueOf(4), list.get(0).getId());
		assertEquals(Long.valueOf(3), list.get(1).getId());
	}

}