package ru.it.sd.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.FileInfo;
import ru.it.sd.service.FileService;
import ru.it.sd.util.ResourceMessages;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Контроллер для работы с файлами
 *
 * @author quadrix
 * @since 24.10.2017
 */
@RestController
@RequestMapping(value = "/rest/service/file", produces = "application/json;charset=UTF-8")
public class FileRestController {

	private final FileService fileService;

	@Autowired
	public FileRestController(FileService fileService) {
		this.fileService = fileService;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public FileInfo upload(@RequestParam("uploaded-file") MultipartFile file, HttpServletRequest request) {
		try {
			return fileService.createTempFileInfo(file.getOriginalFilename(), request.getInputStream());
		} catch (IOException e) {
			throw new ServiceException(ResourceMessages.getMessage("error.read.file", file.getOriginalFilename()));
		}
	}
}
