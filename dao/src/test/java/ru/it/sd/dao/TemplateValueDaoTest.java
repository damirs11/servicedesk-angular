package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.Template;
import ru.it.sd.model.TemplateValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @author nsychev
 * @since 06.09.2018
 */
@Sql("TemplateValueDaoTest.sql")
public class TemplateValueDaoTest extends AbstractDaoTest {
    
    @Autowired
    private TemplateValueDao dao;

    @Test
    private void testRead() {
        TemplateValue value = dao.read(1L);
        assertEquals(value.getId(), Long.valueOf(1L));
        assertEquals(Long.valueOf((String) value.getValue()), Long.valueOf(123L));
    }

    @Test
    private void testFilter() {
        Map<String, String> filter = new HashMap<>();
        filter.put("templateId", "1");
        List<TemplateValue> values = dao.list(filter);
        assertEquals(values.size(), 2);
    }
}