package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @author quadrix
 * @since 05.05.2017
 */
@Sql("GrantDaoTest.sql")
public class GrantDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(GrantDaoTest.class);

	@Autowired
	private GrantDao dao;

	@Test
	private void testList() {
		Map<String, String> filter = new HashMap<>();
		filter.put(SortingInfo.SORTING_PARAM_NAME, "id-asc");
		List<Grant> list = dao.list(filter);
		assertEquals(list.size(), 3);
		assertEquals(list.get(0).getId(), Long.valueOf(1));
		assertEquals(list.get(1).getId(), Long.valueOf(2));
		assertEquals(list.get(2).getId(), Long.valueOf(4));

		assertEquals(list.get(0).getRead(), GrantRule.ALWAYS);
		assertEquals(list.get(0).getCreate(), GrantRule.NONE);

		assertEquals(list.get(1).getRead(), GrantRule.ALWAYS);
		assertEquals(list.get(1).getCreate(), GrantRule.ALWAYS);
		assertEquals(list.get(1).getUpdate(), GrantRule.EXECUTOR);

	}
}