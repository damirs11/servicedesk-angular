package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.ServiceCall;

import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author quadrix
 * @since 05.05.2017
 */
@Sql("ServiceCallDaoTest.sql")
public class ServiceCallDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceCallDaoTest.class);

	@Autowired
	private ServiceCallDao dao;

	@Test
	private void testRead(){
		ServiceCall serviceCall = dao.read(1L);
		assertEquals(serviceCall.getId().longValue(), 1L);
		serviceCall = dao.read(3L);
		assertEquals(serviceCall.getId(), Long.valueOf(3L));
		assertEquals(serviceCall.getServiceLevelAgreement().getId(), Long.valueOf(1L));
		assertEquals(serviceCall.getServiceLevelAgreement().getService().getId(), Long.valueOf(1L));
	}
	@Test
	private void testList(){
		List<ServiceCall> list = dao.list(new HashMap<>());
		assertEquals(list.size(), 3);
	}

}