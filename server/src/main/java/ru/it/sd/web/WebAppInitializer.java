package ru.it.sd.web;

import ru.it.sd.exception.ServiceException;
import ru.it.sd.spring.SpringDaoConfig;
import ru.it.sd.spring.SpringSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import ru.it.sd.spring.SpringServiceConfig;
import ru.it.sd.spring.SpringWebConfig;
import ru.it.sd.util.ResourceMessages;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;

/**
 * Инициализация веб-приложения, аналог файла "web.xml"
 *
 * @author quadrix
 * @since 27.12.2015
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
		return new Class<?>[]{SpringServiceConfig.class, SpringDaoConfig.class, SpringSecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{SpringWebConfig.class};
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[]{};
	}

	private String getAppTempDirectory() {
		File tmpFile = null;
		try {
			tmpFile = File.createTempFile("temp-file", "tmp");
			return tmpFile.getParent() + "/sd";
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(ResourceMessages.getMessage("error.get.temp.directory"));
		} finally {
			if (tmpFile != null) tmpFile.delete();
		}
	}

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		// Директория для загрузки файлов
		String uploadDirectory = getAppTempDirectory();
		// Настройка загрузки файлов
		// Ограничение 1GB для файлов, до 60MB файлы хранятся в ОЗУ
		MultipartConfigElement multipartConfigElement =
				new MultipartConfigElement(uploadDirectory, MAX_FILE_SIZE_IN_BYTES, MAX_FILE_SIZE_IN_BYTES, MIN_FILE_SIZE_TO_HDD_IN_BYTES);
		registration.setMultipartConfig(multipartConfigElement);
		registration.addMapping("/");
	}

}