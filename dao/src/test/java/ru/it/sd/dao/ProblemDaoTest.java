package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.Test;
import ru.it.sd.model.Problem;

import java.util.Collections;
import java.util.List;

/**
 * @author quadrix
 * @since 05.05.2017
 */
@Sql("ProblemDaoTest.sql")
public class ProblemDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ProblemDaoTest.class);

	@Autowired
	private ProblemDao dao;

	@Test
	private void testList() {
		List<Problem> problems = dao.list(Collections.emptyMap());
		LOG.info(problems.toString());
	}

}