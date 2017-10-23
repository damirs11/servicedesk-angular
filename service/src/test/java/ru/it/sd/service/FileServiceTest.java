package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.it.sd.model.DynamicAuthentication;
import ru.it.sd.model.FileInfo;
import ru.it.sd.model.Person;
import ru.it.sd.model.User;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Тестирование сервиса для работы с файлами
 *
 * @author quadrix
 * @since 24.10.2017
 */
public class FileServiceTest extends AbstractServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(FileServiceTest.class);

	@Autowired
	private FileService fileService;

	@BeforeClass
	private void init() {
		Person person = new Person();
		person.setFirstName("Иван");
		person.setLastName("Иванов");

		User user = new User();
		user.setPerson(person);

		DynamicAuthentication auth = mock(DynamicAuthentication.class);
		when(auth.getUser()).thenReturn(user);
		when(auth.isAuthenticated()).thenReturn(true);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	private void testCreateTempFile() {
		String name = fileService.createTempFile(getData());
		logger.debug(name);

		FileInfo info = fileService.createTempFileInfo("йцуке.txt", getData());
		logger.debug(info.toString());
		assertEquals(info.getAuthor().getFirstName(), "Иван");
		assertEquals(info.getName(), "йцуке.txt");
		assertEquals(info.getSize(), 6);
	}

	@Test
	private void testExctractId() {
		assertEquals(fileService.extractTempFileId("sd-dth6767870184675445737.tmp"), "6767870184675445737");
	}

	private InputStream getData() {
		return new ByteArrayInputStream("qwerty".getBytes());
	}
}