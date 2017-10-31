package ru.it.sd.service;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.FileInfo;
import ru.it.sd.util.ResourceMessages;

import java.io.*;
import java.util.Date;

/**
 * Сервис для работы с файлами
 *
 * @author quadrix
 * @since 24.10.2017
 */
@Service
public class FileService {

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	private static String FILE_PREFIX = "sd-dth";
	private static String FILE_SUFFIX = ".tmp";

	private final SecurityService securityService;

	@Autowired
	public FileService(SecurityService securityService) {
		this.securityService = securityService;
	}

	/**
	 * Внутренняя функция создания временного файла
	 * @param is данные файлы
	 * @return пару значений - название временного файла и его размер (не более 2ГБ)
	 */
	private Pair<String, Integer> createTempFileInternal(InputStream is) {
		MutablePair<String, Integer> result = new MutablePair<>();
		try {
			File temp = File.createTempFile(FILE_PREFIX, FILE_SUFFIX);
			String generatedName = temp.getAbsolutePath();
			String id = extractTempFileId(temp.getName());
			result.setLeft(id);
			logger.debug("Temporary file was created \"{}\" with id \"{}\" ", generatedName, id);
			temp.deleteOnExit();
			try (FileOutputStream os = new FileOutputStream(temp)) {
				int size = IOUtils.copy(is, os);
				result.setRight(size);
			} finally {
				is.close();
			}
			return result;
		} catch (IOException e) {
			throw new ServiceException(ResourceMessages.getMessage("error.create.file"));
		}
	}

	String extractTempFileId(String name) {
		return name.substring(FILE_PREFIX.length(), name.length() - FILE_SUFFIX.length());
	}

	/**
	 * Создает временный файл. Файл существует в файловой системе до конца жизни приложения
	 *
	 * @param is данные файла
	 * @return сгенерированное название файла. Полный путь
	 */
	@PreAuthorize("isAuthenticated()")
	public String createTempFile(InputStream is) {
		return createTempFileInternal(is).getLeft();
	}

	/**
	 * Создает временный файл и возвращает обобщенную информацию о нем
	 *
	 * @param fileName название файла от клиента
	 * @param is данные файла
	 * @return подробная информаци о файле + сведения об авторе
	 */
	@PreAuthorize("isAuthenticated()")
	public FileInfo createTempFileInfo(String fileName, InputStream is) {
		Pair<String, Integer> temp = createTempFileInternal(is);
		String id = temp.getLeft();
		int size = temp.getRight();

		FileInfo info = new FileInfo();
		info.setFileId(id);
		info.setName(fileName);
		info.setAuthor(securityService.getCurrentUser().getPerson());
		info.setCreationDate(new Date());
		info.setSize(size);
		return info;
	}
}
