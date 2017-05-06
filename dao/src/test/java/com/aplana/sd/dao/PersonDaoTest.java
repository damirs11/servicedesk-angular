package com.aplana.sd.dao;

import com.aplana.sd.model.Organization;
import com.aplana.sd.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;

import java.util.List;

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
	private void testFindAll() {
		List<Person> list = dao.findAll();
		assertEquals(4, list.size());
		LOG.debug(list.toString());
	}
}