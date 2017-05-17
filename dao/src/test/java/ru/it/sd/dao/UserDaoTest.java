package ru.it.sd.dao;

import ru.it.sd.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * @author quadrix
 * @since 05.05.2017
 */
@Sql("UserDaoTest.sql")
public class UserDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(UserDaoTest.class);

	@Autowired
	private UserDao dao;

	@Test
	private void testFindByLogin() {
		User user = dao.findByLogin("manager");
		assertEquals(3, user.getId());
		LOG.debug(user.toString());

		user = dao.findByLogin("admin");
		assertEquals(2, user.getId());
		LOG.debug(user.toString());
	}
}