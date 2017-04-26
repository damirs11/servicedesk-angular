package com.aplana.sd.spring;

import com.aplana.sd.web.AppLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Конфигурация контекста Spring
 *
 * quadrix created on 27.12.2015.
 */
@Configuration
@EnableWebMvc
@ComponentScan({"com.aplana.sd.web"})
public class SpringWebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private AppLoggingInterceptor appLoggingInterceptor;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/img/**").addResourceLocations("/img/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/index.html").addResourceLocations("/index.html");
    }

	/**
	 * Для того, чтобы работала загрузка файлов
	 * @return
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver(){
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("utf-8");
		return commonsMultipartResolver;
	}

	/**
	 * Для логирования
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(appLoggingInterceptor);
	}
}