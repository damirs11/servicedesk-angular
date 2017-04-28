package com.aplana.sd.spring;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Конфигурация спринга в ДАО-слое
 *
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a> created on 11.01.2016.
 */
@Configuration
@EnableCaching(mode = AdviceMode.ASPECTJ)
@ComponentScan("com.aplana.sd.dao")
@EnableJpaRepositories
public class SpringDaoConfig extends CachingConfigurerSupport {

	@Bean
	@Override
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
		factory.setConfigLocation(new ClassPathResource("ehcache.xml"));
		return factory;
	}

}