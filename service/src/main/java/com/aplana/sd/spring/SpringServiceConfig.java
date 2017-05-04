package com.aplana.sd.spring;

import com.jolbox.bonecp.BoneCPDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Конфигурация спринга в сервисах
 *
 * @author quadrix
 * @since 11.01.2016
 */
@Configuration
@ComponentScan({"com.aplana.sd.service"})
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableGlobalMethodSecurity(prePostEnabled = true, mode= AdviceMode.ASPECTJ)
@EnableAsync
@EnableScheduling
@PropertySource("classpath:application.properties")
public class SpringServiceConfig extends GlobalMethodSecurityConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(SpringServiceConfig.class);

	@Autowired
	private DataSource dataSource;
	@Resource
	private Environment env;

	/**
	 * Создание бина для доступа к бд
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean(destroyMethod = "close")
	public DataSource dataSource() throws SQLException {
		LOG.debug("Connecting to database: " + env.getProperty("sd_db_url"));
		BoneCPDataSource ds = new BoneCPDataSource();
 		ds.setDriverClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
 		ds.setJdbcUrl(env.getProperty("sd_db_url"));
 		ds.setUser(env.getProperty("sd_db_user"));
 		ds.setPassword(env.getProperty("sd_db_password"));
		return ds;
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

	/**
	 * Подключение загрузчика значений спринговых констант из properties-файла
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}