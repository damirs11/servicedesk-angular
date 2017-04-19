package com.aplana.sd.spring;

import com.jolbox.bonecp.BoneCPDataSource;
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
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a> created on 11.01.2016.
 */
@Configuration
@ComponentScan("com.aplana.sd.dao")
public class SpringDaoTestConfig {

	@Autowired
	private DataSource dataSource;

	/**
	 * Создание бина для доступа к бд
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean(destroyMethod = "close")
	public DataSource dataSource() throws SQLException {
		BoneCPDataSource ds = new BoneCPDataSource();
        ds.setDriverClass("org.hsqldb.jdbcDriver");
        ds.setJdbcUrl("jdbc:hsqldb:mem:testdb");
        ds.setUser("sa");
        ds.setPassword("");
		return ds;
	}

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
				/*.addScript("file:../database/test/hsql_init.sql")
				.addScript("file:../database/db_create.sql")
				.addScript("file:../database/test/usim_forecast_cons_vendor.sql")
				.addScript("file:../database/test/usim_calendar_ext.sql")
				.addScript("file:../database/db_create_constraints.sql")*/
				.build();
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws SQLException {
		return new DataSourceTransactionManager(dataSource);
	}

}