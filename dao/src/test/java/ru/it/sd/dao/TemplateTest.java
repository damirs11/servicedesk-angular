package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.EntityType;
import ru.it.sd.model.Template;
import ru.it.sd.model.TemplateValue;
import ru.it.sd.model.Workorder;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@Sql("WorkorderTemplateTest.sql")
public class TemplateTest extends AbstractDaoTest {

    @Autowired
    WorkorderDao workorderDao;

    @Test
    public void testGetResultSet() {
        Template template = new Template();
        template.setId(1L);
        template.setEntityType(EntityType.WORKORDER);
        Workorder workorder = workorderDao.getTemplate(template);
    }
}