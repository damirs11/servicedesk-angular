package ru.it.sd.dao;

import ru.it.sd.model.Organization;
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
@Sql("OrganizationDaoTest.sql")
public class OrganizationDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(OrganizationDaoTest.class);

	@Autowired
	private OrganizationDao dao;

	@Test
	private void test() {
		LOG.info("Context was loaded");
	}

	@Test
	private void testFindAll() {
		List<Organization> list = dao.list();
		assertEquals(2, list.size());
		LOG.debug(list.toString());
	}
}