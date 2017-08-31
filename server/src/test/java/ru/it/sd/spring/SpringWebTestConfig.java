package ru.it.sd.spring;

import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Vitalii Samolovskikh
 */
@Configuration
@EnableWebMvc
@ComponentScan(
        basePackages = {"ru.it.sd.dao", "ru.it.sd.service", "ru.it.sd.web.controller.rest", "ru.it.sd.security", "ru.it.sd.hp"},
		excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SpringWebConfig.class})
)
public class SpringWebTestConfig extends WebMvcConfigurerAdapter {

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
				.addScript("file:../dao/src/test/resources/sql/hsql_init.sql")
				.addScript("file:../dao/src/test/resources/sql/ddl.sql")
				.addScript("file:../dao/src/test/resources/sql/ddl_constraints.sql")
				.build();
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws SQLException {
		return new DataSourceTransactionManager(dataSource);
	}

}