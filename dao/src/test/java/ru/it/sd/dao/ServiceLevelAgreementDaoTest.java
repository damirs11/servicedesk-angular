package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.ServiceLevelAgreement;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author nsychev
 * @since 27.03.2019
 */
//TODO
@Sql("ServiceLevelAgreementDaoTest.sql")
public class ServiceLevelAgreementDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceLevelAgreementDaoTest.class);

	@Autowired
	private ServiceLevelAgreementDao dao;

	@Test
	private void testRead(){
		ServiceLevelAgreement serviceLevelAgreement = dao.read(1L);
		assertEquals(serviceLevelAgreement.getId().longValue(), 1L);
	}
	@Test
	private void testList(){
		List<ServiceLevelAgreement> list = dao.list(null);
		assertEquals(list.size(), 4);
	}

}