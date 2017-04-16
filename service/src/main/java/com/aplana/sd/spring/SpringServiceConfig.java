package com.aplana.sd.spring;

import com.jolbox.bonecp.BoneCPDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Конфигурация спринга в сервисах
 *
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a> created on 11.01.2016.
 */
@Configuration
@ComponentScan({"com.aplana.sd.service"})
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableGlobalMethodSecurity(prePostEnabled = true, mode= AdviceMode.ASPECTJ)
@EnableAsync
@EnableScheduling
public class SpringServiceConfig extends GlobalMethodSecurityConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(SpringServiceConfig.class);
	private static final String DATASOURCE_JNDI_NAME = "jdbc/DataSource";

	@Autowired
	private DataSource dataSource;

	/*@Bean
	public DataSource dataSource() {
		LOG.debug("Try to get datasource with JNDI = " + DATASOURCE_JNDI_NAME);
		JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		return dsLookup.getDataSource(DATASOURCE_JNDI_NAME);
	}*/

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
	public PlatformTransactionManager transactionManager() throws Exception {
		LOG.debug("Transaction manager initialization");
		return new DataSourceTransactionManager(dataSource);
	}

	// Обработка выражения для проверки прав доступа
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        //expressionHandler.setPermissionEvaluator(grantChecker);
        return expressionHandler;
    }

}