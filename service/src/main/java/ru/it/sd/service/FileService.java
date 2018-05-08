package ru.it.sd.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.FileInfoDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.IAttachedItemDao;
import ru.it.sd.model.FileInfo;
import ru.it.sd.util.ResourceMessages;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для работы с файлами
 *
 * @author quadrix
 * @since 24.10.2017
 */
@Service
public class FileService extends CrudService<FileInfo>{

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	@Value("${sd_upload_webserver_dir}")
    private String webserverDir;
    @Value("${sd_upload_appserver_dir}")
    private String appserverDir;

	private static String FILE_PREFIX = "sd-dth";
	private static String FILE_SUFFIX = ".tmp";

	private final SecurityService securityService;
    private FileInfoDao fileInfoDao;
    private IAttachedItemDao iAttachedItemDao;
	@Autowired
	public FileService(SecurityService securityService, FileInfoDao fileInfoDao, IAttachedItemDao iAttachedItemDao) {
	    this.securityService = securityService;
	    this.fileInfoDao = fileInfoDao;
	    this.iAttachedItemDao = iAttachedItemDao;
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

    @Override
    public FileInfo read(long id) {
        return fileInfoDao.read(id);
    }

    @Override
    public List<FileInfo> list(Map<String, String> filter) {
        return fileInfoDao.list(filter);
    }

    @Override
    public int count(Map<String, String> filter) {
        return fileInfoDao.count(filter);
    }

    @Override
    public FileInfo create(FileInfo entity) {
        try{
            String tmpFileName = FILE_PREFIX + entity.getFileId() + FILE_SUFFIX;
            String tmpPath = System.getProperty("java.io.tmpdir");
            if(!tmpPath.substring(tmpPath.length()-1).equals("\\")) tmpPath+= "\\";
            FileUtils.copyFileToDirectory(new File(tmpPath + tmpFileName) ,new File(webserverDir));
            entity.setPath(appserverDir + tmpFileName);
            long id = iAttachedItemDao.create(entity);
            FileUtils.forceDelete(new File(webserverDir + tmpFileName));
            return fileInfoDao.read(id);
        } catch (Exception e){
            throw new ServiceException("Возникли проблемы при создании вложения. " + e.getMessage(), e);
        }
    }

    @Override
    public FileInfo update(FileInfo entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        try{
            iAttachedItemDao.delete(id);
        } catch (Exception e){
            throw new ServiceException("Возникли проблемы при удалении вложения. " + e.getMessage(), e);
        }
    }

    @Override
    public FileInfo patch(FileInfo entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }
}
