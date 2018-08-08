package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.Template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
/**
 * @author nsychev
 * @since 19.02.2018
 */
@Sql("TemplateDaoTest.sql")
public class TemplateDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(TemplateDaoTest.class);

	@Autowired
	private TemplateDao templateDao;



	@Test
	private void testRead() {
		//todo проверить distinct
		Template template = templateDao.read(1L);
		assertEquals(template.getId(), Long.valueOf(1L));
	}
	@Test
	private void filter(){
		Map<String, String> filter = new HashMap<>();
		filter.put("entityId", "724041768");
		List<Template> templateList = templateDao.list(filter);
		assertEquals(templateList.size(), 2L);
	}

}