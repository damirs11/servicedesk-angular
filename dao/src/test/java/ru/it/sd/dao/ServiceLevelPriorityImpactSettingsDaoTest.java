package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.ServiceLevelPriorityImpactSetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author quadrix
 * @since 21.11.2017
 */
@Sql("ServiceLevelPriorityImpactSettingDaoTest.sql")
public class ServiceLevelPriorityImpactSettingsDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceLevelPriorityImpactSettingsDaoTest.class);

	@Autowired
	private ServiceLevelPriorityImpactSettingDao dao;

	@Test
	private void testRead() {
		ServiceLevelPriorityImpactSetting serviceLevelPriorityImpactSetting = dao.read(1L);
		assertNotNull(serviceLevelPriorityImpactSetting);
		assertEquals(serviceLevelPriorityImpactSetting.getId(), Long.valueOf(1L));
    }

	@Test
	private void testList() {
		List<ServiceLevelPriorityImpactSetting> list = dao.list(null);
		assertNotNull(list);
		assertEquals(list.size(), 4);
		Map<String, String> filter = new HashMap<>();
		filter.put("serviceLevelId", "2");
		list = dao.list(filter);
		assertNotNull(list);
		assertEquals(list.size(), 1);
	}
}