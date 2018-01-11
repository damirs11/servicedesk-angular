package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.ServiceRelationEntry;

import static org.testng.Assert.assertEquals;

@Sql("ServiceRelationDaoTest.sql")
public class ServiceRelationDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceRelationDaoTest.class);

	@Autowired
	private ServiceRelationEntryDao serviceRelationEntryDao;

	@Test
	private void testRead() {
        ServiceRelationEntry serviceRelationEntry = serviceRelationEntryDao.read(12345L);
        assertEquals(serviceRelationEntry.getId().longValue(), 12345L);
        assertEquals(serviceRelationEntry.getChange().getId().longValue(), 331234L);
        assertEquals(serviceRelationEntry.getType().getId().longValue(), 281478224675129L);
        assertEquals(serviceRelationEntry.getProblem(), 0);
    }

}