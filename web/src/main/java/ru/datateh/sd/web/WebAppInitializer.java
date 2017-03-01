package ru.datateh.sd.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;
import java.io.IOException;

/**
 * Инициализация веб-приложения, аналог файла "web.xml"
 *
 * @author quadrix created on 27.12.2015.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(WebAppInitializer.class);

	private static final long MAX_FILE_SIZE_IN_BYTES = 1024 * 1024 * 1024L; // 1GB
	private static final int MIN_FILE_SIZE_TO_HDD_IN_BYTES = 1024 * 1024 * 60; // 60MB

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		//return new Class<?>[]{SpringServiceConfig.class, SpringDaoConfig.class, SpringSecurityConfig.class, };
		return new Class<?>[]{};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{SpringWebConfig.class};
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[]{new SessionCheckFilter()};
	}

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		// Директория для загрузки файлов
		String uploadDirectory = null;
		try {
			File tmpFile = File.createTempFile("temp-file", "tmp");
			uploadDirectory = tmpFile.getParent() + "/sd";
			tmpFile.delete();
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		// Настройка загрузки файлов
		// Ограничение 1GB для файлов, до 60MB файлы хранятся в ОЗУ
		MultipartConfigElement multipartConfigElement =
				new MultipartConfigElement(uploadDirectory, MAX_FILE_SIZE_IN_BYTES, MAX_FILE_SIZE_IN_BYTES, MIN_FILE_SIZE_TO_HDD_IN_BYTES);
		registration.setMultipartConfig(multipartConfigElement);
		registration.addMapping("/");
	}

}