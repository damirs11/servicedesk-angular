package ru.datateh.sd.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.datateh.sd.dao.DBUtils;

import javax.sql.DataSource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Конфигурация спринга в сервисах
 *
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a> created on 11.01.2016.
 */
@Configuration
@ComponentScan({"ru.datateh.sd.service", "ru.datateh.sd.security"})
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableGlobalMethodSecurity(prePostEnabled = true, mode= AdviceMode.ASPECTJ)
@EnableAsync
@EnableScheduling
public class SpringServiceConfig extends GlobalMethodSecurityConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(SpringServiceConfig.class);
	private static final String DATASOURCE_JNDI_NAME = "jdbc/DataSource";

	@Autowired
	private DataSource dataSource;

	@Bean
	public DataSource dataSource() {
		LOG.debug("Try to get datasource with JNDI = " + DATASOURCE_JNDI_NAME);
		JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		return dsLookup.getDataSource(DATASOURCE_JNDI_NAME);
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		LOG.debug("Transaction manager initialization");
		return new DataSourceTransactionManager(dataSource);
	}

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        //expressionHandler.setPermissionEvaluator(grantChecker);
        return expressionHandler;
    }

}