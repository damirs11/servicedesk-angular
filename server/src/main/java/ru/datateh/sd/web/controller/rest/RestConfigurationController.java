package ru.datateh.sd.web.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.datateh.sd.model.AppRole;
import ru.datateh.sd.model.AppUser;
import ru.datateh.sd.model.Operation;
import ru.datateh.sd.service.SecurityService;
import ru.datateh.sd.util.ResourceMessages;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.Manifest;

/**
 * Контроллер для работы с базовыми настройками приложения:
 * <ul>
 * <li>информация о текущем пользователе</li>
 * <li>строковые константы</li>
 * </ul>
 *
 * @author quadrix
 * 07.10.2016 14:37
 */
@RestController
@RequestMapping(value = "/rest/service/config", method = RequestMethod.GET, produces = "application/json")
@EnableWebMvc
public class RestConfigurationController {

	private static final Logger LOG = LoggerFactory.getLogger(RestConfigurationController.class);

	@Autowired
	private SecurityService securityService;

	@RequestMapping("/getInfo")
	@ResponseBody
	public Map<String, Object> getInfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		result.put("user", getUserCompleteInfo());

		Map<Object, Object> translate = new HashMap<>();
		translate.putAll(getTranslates());
		translate.putAll(getManifestInfo(request.getServletContext()));
		result.put("translate", translate);
		return result;
	}

	/**
	 * Возвращает маппинг текстовых констант
	 *
	 * @return
	 */
	private Properties getTranslates() {
		ResourceBundle resource = ResourceMessages.getResourceBundle();

		Properties properties = new Properties();
		Enumeration<String> keys = resource.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			properties.put(key, resource.getString(key));
		}
		return properties;
	}

	/**
	 * Возвращает информацию из манифеста о версии приложения, даты сборки и т.д.
	 * @return
	 */
	private Map<Object, Object> getManifestInfo(ServletContext servletContext) {
		Manifest mf = new Manifest();
		try (InputStream mfStream = servletContext.getResourceAsStream("/META-INF/MANIFEST.MF")) {
			mf.read(mfStream);
		} catch (IOException e) {
			LOG.error(ResourceMessages.getMessage("error.read.manifest.file"), e);
		}
		return mf.getMainAttributes();
	}

	/**
	 * Возвращает информацию о текущем пользователе
	 */
	private Map<String, Object> getUserCompleteInfo() {
		Map<String, Object> result = new HashMap<>();
		// Информация о пользователе
		AppUser user = securityService.currentUser();
		// Если пользователь не аутентифицирован
		if (user == null) {
			result.put("login", ResourceMessages.getMessage("default.login"));
			result.put("name", ResourceMessages.getMessage("default.login"));
			result.put("roles", new HashSet<>());
			result.put("grants", new HashSet<>());
			return result;
		}
		// Если пользователь аутентифицирован
		result.put("login", user.getLogin());
		result.put("name", user.getName());
		// Информация о ролях пользователя и правах доступа
		Set<String> grants = new HashSet<>();
		Set<String> roles = new HashSet<>();
		if (user.getRoles() != null) {
			for (AppRole role : user.getRoles()) {
				roles.add(role.getName());
				grants.add(role.getAuthority());
				for (Operation operation : role.getOperations()) {
					grants.add(operation.getAuthority());
				}
			}
		}
		result.put("roles", roles);
		result.put("grants", grants);
		return result;
	}
}