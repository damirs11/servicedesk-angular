package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.AttributeAccess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

@Sql("AttributeAccessDaoTest.sql")
public class AttributeDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(AttributeDaoTest.class);

    @Autowired
    private AttributeAccessDao dao;

    @Autowired
    private RoleDao roleDao;
	@Test
	private void testRead() {
        AttributeAccess attributeAccess = dao.read(1L);
        assertEquals(attributeAccess.getId(), Long.valueOf(1L));
    }

    @Test
    private void testList() {
        List<AttributeAccess> attributeAccess = dao.list(null);
        assertEquals(attributeAccess.size(), 6);

    }


    @Test
    private void testFilter() {
        Map<String, String> filter = new HashMap<>();
        filter.put("attributeId","11");
        filter.put("grant", "5");
        List<AttributeAccess> attributeAccess = dao.list(filter);
        assertEquals(attributeAccess.size(), 1);

    }

}