package ru.it.sd.dao;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.*;

import static org.testng.Assert.*;

/**
 * @author quadrix
 * @since 05.05.2017
 */
@Sql("WorkorderDaoTest.sql")
public class WorkorderDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(WorkorderDaoTest.class);

	@Autowired
	private WorkorderDao dao;

	@Test
	private void testFindOne() {
		Workorder workorder = dao.findOne(12L);
		assertNull(workorder);
		workorder = dao.findOne(245242424L);
		assertNotNull(workorder);
		assertEquals(281493838237866L, workorder.getId().longValue());
		assertEquals(53081L, workorder.getNo().longValue());
		assertEquals(EntityStatus.WORKORDER_CLOSED, workorder.getStatus());
		assertEquals(EntityCategory.WORKORDER_TASK, workorder.getCategory());
		assertEquals(EntityClosureCode.WORKORDER_COMPLETED, workorder.getClosureCode());
		assertEquals(111222, workorder.getChange().getId().longValue());
	}
}