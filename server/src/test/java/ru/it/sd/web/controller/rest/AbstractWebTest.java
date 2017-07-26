package ru.it.sd.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.it.sd.spring.SpringWebTestConfig;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @author quadrix
 * @since 24.06.2016 17:03
 */
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractWebTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	private DataSource dataSource;
	/** Для запросов с именованными параметрами */
	protected NamedParameterJdbcTemplate namedJdbc;
	/** Для запросов с анонимными параметрами */
	protected JdbcTemplate jdbc;

	@PostConstruct
	private void init() {
		namedJdbc = new NamedParameterJdbcTemplate(dataSource);
		jdbc = (JdbcTemplate) namedJdbc.getJdbcOperations();
	}
}