package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.ServiceLevel;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author quadrix
 * @since 21.11.2017
 */
@Sql("ServiceLevelDaoTest.sql")
public class ServiceLevelDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceLevelDaoTest.class);

	@Autowired
	private ServiceLevelDao dao;

	@Test
	private void testRead() {
		ServiceLevel serviceLevel = dao.read(1L);
		assertNotNull(serviceLevel);
		assertEquals(serviceLevel.getId(), Long.valueOf(1L));
    }

	@Test
	private void testList() {
		List<ServiceLevel> list = dao.list(null);
		assertNotNull(list);
		assertEquals(list.size(), 4);
	}
}