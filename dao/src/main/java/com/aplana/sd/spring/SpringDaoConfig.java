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

/**
 * Конфигурация спринга в ДАО-слое
 *
 * @author quadrix
 * @since 11.01.2016
 */
@Configuration
@EnableCaching(mode = AdviceMode.ASPECTJ)
@ComponentScan("com.aplana.sd.dao")
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