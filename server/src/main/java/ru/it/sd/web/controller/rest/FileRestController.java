package ru.it.sd.web.controller.rest;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.FileInfo;
import ru.it.sd.service.FileService;
import ru.it.sd.service.utils.FileUtils;
import ru.it.sd.util.ResourceMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Контроллер для работы с файлами
 *
 * @author quadrix
 * @since 24.10.2017
 */
@RestController
@RequestMapping(value = "/rest/service/file", produces = "application/json;charset=UTF-8")
public class FileRestController {

    private static final Logger logger = LoggerFactory.getLogger(FileRestController.class);

    @Value("${sd_ftp_server}")
    private String server;

    @Value("${sd_ftp_port}")
    private String port;

    @Value("${sd_ftp_login}")
    private String login;

    @Value("${sd_ftp_password}")
    private String password;

    @Value("${sd_ftp_homepath}")
    private String homepath;

	private final FileService fileService;

	@Autowired
	public FileRestController(FileService fileService) {
		this.fileService = fileService;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public FileInfo upload(@RequestParam("uploaded-file") MultipartFile file, HttpServletRequest request) {
		try {
			return fileService.createTempFileInfo(file.getOriginalFilename(), file.getInputStream());
		} catch (IOException e) {
			throw new ServiceException(ResourceMessages.getMessage("error.read.file", file.getOriginalFilename()));
		}
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@RequestParam Long id, HttpServletResponse response){
        FileInfo fileInfo = fileService.read(id);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename="+fileInfo.getName());

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, Integer.valueOf(port));
            ftpClient.login(login, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            String path = homepath +fileInfo.getEntityType().getAlias() +"/"+ FileUtils.getFTPPathFromOid(fileInfo.getId(), fileInfo.getEntityId());
            //Записываем файл в поток
            if(!ftpClient.retrieveFile( path, response.getOutputStream()))
                throw new ServiceException("Файл не найден");
            response.flushBuffer();
        } catch (IOException e) {
            throw new ServiceException("Ошибка скачивания файла ", e.getMessage());
        } finally {
            if(ftpClient.isConnected()){
                try {
                    ftpClient.disconnect();
                } catch (IOException e){
                    //do nothing
                }
            }
        }
    }
    @RequestMapping(value = "/uploadList", method = RequestMethod.POST)
    public List<FileInfo> uploadList(MultipartHttpServletRequest request) {
        List<FileInfo> result = new ArrayList<>();
        Iterator<String> iterator = request.getFileNames();
        while (iterator.hasNext()){
            String fileName = iterator.next();
            MultipartFile file = request.getFile(fileName);
            try {
                FileInfo fileInfo = fileService.createTempFileInfo(file.getOriginalFilename(), file.getInputStream());
                result.add(fileInfo);
            } catch (IOException e) {
                throw new ServiceException("error.read.file", file.getOriginalFilename());
            }
        }
        return result;
    }

}

