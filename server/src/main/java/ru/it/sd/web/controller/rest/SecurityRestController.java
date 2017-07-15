package ru.it.sd.web.controller.rest;

import ru.it.sd.service.SecurityService;
import ru.it.sd.util.ResourceMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Сервисы для работы с безопасностью
 *
 * @author quadrix
 * @since 22.04.2017
 */
@RestController
@RequestMapping(value = "/rest/service/security", produces = "application/json;charset=UTF-8")
public class SecurityRestController extends AbstractController{

	private static final Logger LOG = LoggerFactory.getLogger(SecurityRestController.class);

	private static final String LOGIN_PARAM = "login";
	private static final String PASSWORD_PARAM = "password";
	private static final String OLD_PASSWORD_PARAM = "oldPassword";
	private static final String NEW_PASSWORD_PARAM = "newPassword";

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(@RequestBody String json) throws IOException {
		Map<String, String> params = objectMapper.readValue(json, Map.class);
		securityService.loginUser(params.get(LOGIN_PARAM), params.get(PASSWORD_PARAM));
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request) throws IOException {
		securityService.logoutUser();
		// уничтожаем старую сессию
		HttpSession session = request.getSession();
		if (Objects.nonNull(session)) {
			LOG.debug(ResourceMessages.getMessage("http.session.invalidate", session.getId()));
			session.invalidate();
		}
	}

	@RequestMapping(value = "/passwordChange", method = RequestMethod.POST)
	public void passwordChange(@RequestBody String json) throws IOException {
		Map<String, String> params = objectMapper.readValue(json, Map.class);
		securityService.changePassword(params.get(OLD_PASSWORD_PARAM), params.get(NEW_PASSWORD_PARAM));
	}

	/**
	 * Уничтожение текущей сессии пользователя, если она существовала
	 * @param request
	 */
	@Deprecated
	private void recreateSession(HttpServletRequest request) {
		// уничтожаем старую сессию
		HttpSession session = request.getSession();
		if (Objects.nonNull(session)) {
			LOG.debug(ResourceMessages.getMessage("http.session.invalidate", session.getId()));
			session.invalidate();
		}
		// создаем новую сессию
		request.getSession(true);
	}
}