package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.Approval;
import ru.it.sd.model.EntityType;

import static org.testng.Assert.assertEquals;

/**
 * @author quadrix
 * @since 05.05.2017
 */
@Sql("ApprovalDaoTest.sql")
public class ApprovalDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ApprovalDaoTest.class);

	@Autowired
	private ApprovalDao dao;

	@Test
	private void testRead() {
        Approval approval = dao.read(111222L);
        assertEquals(approval.getId(), (Long)111222L);
        assertEquals(approval.getEntityType(), EntityType.CHANGE);
    }

}