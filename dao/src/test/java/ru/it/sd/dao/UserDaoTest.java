package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.Role;
import ru.it.sd.model.User;

import java.util.List;

import static org.testng.Assert.assertEquals;

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
		User user = dao.readByLogin("manager");
		assertEquals(user.getId(), 3);
		LOG.debug(user.toString());

		user = dao.readByLogin("admin");
		assertEquals(user.getId(), 2);
		LOG.debug(user.toString());
	}

	@Test
	private void testRead() {
		User user = dao.read(1);
		assertEquals(user.getLogin(), "test");
		List<Role> roles = user.getRoles();
		assertEquals(roles.size(), 2);

		roles.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
		assertEquals(roles.get(0).getName(), "1");
		assertEquals(roles.get(1).getName(), "2");

	}
}