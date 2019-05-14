package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.RequireField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @author quadrix
 * @since 05.05.2017
 */
@Sql("RequiredFieldDaoTest.sql")
public class RequiredFieldDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(RequiredFieldDaoTest.class);

	@Autowired
	private RequiredFieldDao dao;

	@Test(expectedExceptions = UnsupportedOperationException.class)
	private void testRead(){
		dao.read(1L);
	}
	@Test
	private void testList() {
		Map<String, String> filter = new HashMap<>();
		filter.put("entityTypeId", "1");
		filter.put("statusId", "1");
		List<RequireField> list = dao.list(filter);
		assertEquals(list.size(), 6);
	}

}