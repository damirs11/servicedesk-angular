package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.ServiceLevelPriorityDuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author quadrix
 * @since 21.11.2017
 */
@Sql("ServiceLevelPriorityDurationDaoTest.sql")
public class ServiceLevelPriorityDurationDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceLevelPriorityDurationDaoTest.class);

	@Autowired
	private ServiceLevelPriorityDurationDao dao;

	@Test
	private void testRead() {
		ServiceLevelPriorityDuration read = dao.read(1L);
		assertNotNull(read);
		assertEquals(read.getId(), Long.valueOf(1L));
    }

	@Test
	private void testList() {
		Map<String, String> filter = new HashMap<>();
		filter.put("entityTypeId", "563019801");
		filter.put("serviceLevelPriorityId", "1");
		List<ServiceLevelPriorityDuration> serviceLevelPriorityDurations = dao.list(filter);
		assertNotNull(serviceLevelPriorityDurations);
		assertEquals(serviceLevelPriorityDurations.size(), 1);
	}
}