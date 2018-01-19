package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.BaseCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @author quadrix
 * @since 21.11.2017
 */
@Sql("CodeDaoTest.sql")
public class CodeDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(CodeDaoTest.class);

	@Autowired
	private CodeDao dao;

	@Autowired
	private CodeChildsDao codeChildsDao;

	@Test
	private void testRead() {
        BaseCode code = dao.read(281478224675129L);
        assertEquals(code.getId().longValue(), 281478224675129L);
		assertEquals(code.getName(), "Функционирует");
    }

	@Test
	private void testFolderChilds() {
		Map<String, String> filter = new HashMap<>();
		filter.put("parentId", "1");
		List<BaseCode> code = codeChildsDao.list(filter);
		assertEquals(code.size(), 3);
	}
}