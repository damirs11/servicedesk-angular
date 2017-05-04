package com.aplana.sd.service;

import com.aplana.sd.spring.SpringServiceTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * Базовый класс для юнит-тестов
 * 
 * @author quadrix
 * @since 04.09.2012
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {SpringServiceTestConfig.class})
public abstract class AbstractServiceTest extends AbstractTransactionalTestNGSpringContextTests {

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