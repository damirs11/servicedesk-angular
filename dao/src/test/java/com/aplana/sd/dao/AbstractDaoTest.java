package com.aplana.sd.dao;

import com.aplana.sd.spring.SpringDaoTestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@ContextConfiguration(classes = {SpringDaoTestConfig.class})
public abstract class AbstractDaoTest extends AbstractTransactionalTestNGSpringContextTests {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractDaoTest.class);

    @Autowired
    private DataSource dataSource;
    /** Для запросов с именованными параметрами */
    protected NamedParameterJdbcTemplate namedJdbc;
    /** Для запросов с безымянными параметрами */
    protected JdbcTemplate jdbc;

    @PostConstruct
    private void init() {
        namedJdbc = new NamedParameterJdbcTemplate(dataSource);
        jdbc = (JdbcTemplate) namedJdbc.getJdbcOperations();
    }
}