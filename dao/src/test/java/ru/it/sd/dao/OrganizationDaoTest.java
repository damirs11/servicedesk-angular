package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.Organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

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
	private void testRead() {
		Organization organization = dao.read(1L);
		assertEquals(organization.getId().longValue(), 1L);
		assertEquals(organization.getFolder().getId().longValue(), 12345678L);
	}

	@Test
	private void testFindAll() {
		Map<String, String> filter = new HashMap<>();
		List<Organization> list = dao.list(filter);
		assertEquals(list.size(), 2);
		LOG.debug(list.toString());
	}
}