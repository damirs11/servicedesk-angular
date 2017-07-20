package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

@Sql("WorkorderDaoTest.sql")
@Sql("PersonDaoTest.sql")
@Sql("ChangeDaoTest.sql")
public class WorkorderDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(WorkorderDaoTest.class);

	@Autowired
	private WorkorderDao dao;

	@Test
	private void testFindOne() {
		Workorder workorder = dao.read(12L);
		assertNull(workorder);
		workorder = dao.read(10001L);
		assertNotNull(workorder);
		assertEquals(workorder.getId().longValue(), 10001L);
		assertEquals(workorder.getNo().longValue(), 1001L);
		assertEquals(workorder.getStatus(), EntityStatus.WORKORDER_CLOSED);
		assertEquals(workorder.getCategory(), EntityCategory.WORKORDER_TASK);
		assertEquals(workorder.getClosureCode(), EntityClosureCode.WORKORDER_COMPLETED);
	}

	@Test
	private void testFindByFilter(){

		Map<String, String> firstFilter = new HashMap<>();
		firstFilter.put("initiator","1");
		Map<String, String> secondFilter = new HashMap<>();
		secondFilter.put("initiator","2");
		Map<String, String> thirdFilter = new HashMap<>();
		thirdFilter.put("initiator","1");
		thirdFilter.put("assigneePerson","2");

		List<Workorder> workorders = dao.list(firstFilter);
		assertEquals(workorders.size(),2);
		workorders = dao.list(secondFilter);
		assertEquals(workorders.size(),1);
		workorders = dao.list(thirdFilter);
		assertEquals(workorders.size(),1);
		assertNotNull(workorders.get(0).getAssigneePerson());

		Map<String, String> byChangeFilter = new HashMap<>();
		byChangeFilter.put("change_like","111222");
		workorders = dao.list(byChangeFilter);
		assertEquals(workorders.size(), 1);
	}
}