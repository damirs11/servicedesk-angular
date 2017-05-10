package com.aplana.sd.web.controller.rest;

import com.aplana.sd.service.UserService;
import com.aplana.sd.util.ResourceMessages;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping(value = "/rest/service/security", produces = "application/json")
public class RestSecurityController {

	private static final Logger LOG = LoggerFactory.getLogger(RestSecurityController.class);

	private static final String LOGIN_PARAM = "login";
	private static final String PASSWORD_PARAM = "password";

	// Настройка маппинг json в объектные типы
	private static final ObjectMapper objectMapper = new ObjectMapper()
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(@RequestBody String json) throws IOException {
		Map<String, String> params = objectMapper.readValue(json, Map.class);
		userService.loginUser(params.get(LOGIN_PARAM), params.get(PASSWORD_PARAM));
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request) throws IOException {
		userService.logoutUser();
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
		LOG.debug("passwordChange");
		//userService.loginUser(params.get(LOGIN_PARAM), params.get(PASSWORD_PARAM));
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