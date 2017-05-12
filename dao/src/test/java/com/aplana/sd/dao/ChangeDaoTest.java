package com.aplana.sd.dao;

import com.aplana.sd.model.Change;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * @author quadrix
 * @since 05.05.2017
 */
@Sql("ChangeDaoTest.sql")
public class ChangeDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ChangeDaoTest.class);

	@Autowired
	private ChangeDao dao;

	@Test
	private void testFindOne() {
		Change change = dao.findOne(55665L);
		assertNull(change);
		change = dao.findOne(111222L);
		assertNotNull(change);
		assertEquals(111222L, change.getId().longValue());
		assertEquals(112L, change.getNo().longValue());
	}
}