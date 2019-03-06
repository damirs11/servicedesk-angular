package ru.it.sd.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Конфигурация спринга в ДАО-слое для юнит-тестов
 *
 * @author quadrix
 * @since 11.01.2016
 */
@Configuration
@ComponentScan("ru.it.sd.dao")
public class SpringDaoTestConfig {

	@Autowired
	private DataSource dataSource;

	/**
	 * Поднимаем инстанс встроенной БД с url = jdbc:hsqldb:mem:testdb
	 *
	 * @return
	 * @throws SQLException
	 */
	@Bean(destroyMethod = "shutdown")
	public EmbeddedDatabase dataSourceEmbd() throws SQLException {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder
				.setType(EmbeddedDatabaseType.HSQL) //.H2 or .DERBY
				.addScript("classpath:/sql/hsql_init.sql")
				.addScript("classpath:/sql/ddl.sql")
				.addScript("classpath:/sql/ddl_constraints.sql")
				.build();
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws SQLException {
		return new DataSourceTransactionManager(dataSource);
	}

}