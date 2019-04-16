package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.Service;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author nsychev
 * @since 27.03.2019
 */
//TODO
@Sql("ServiceDaoTest.sql")
public class ServiceDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceDaoTest.class);

	@Autowired
	private ServiceDao dao;

	@Test
	private void testRead(){
		Service service = dao.read(1L);
		assertEquals(service.getId().longValue(), 1L);
	}
	@Test
	private void testList(){
		List<Service> list = dao.list(null);
		assertEquals(list.size(), 5);
	}

}