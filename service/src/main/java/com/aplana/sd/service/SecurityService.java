package com.aplana.sd.service;

import com.aplana.sd.dao.UserDao;
import com.aplana.sd.model.User;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.aplana.sd.util.ResourceMessages.getMessage;

/**
 * Сервис для работы с информацией о текущих параметрах безопасности
 *
 * @author quadrix
 * @since 07.03.2017
 */
@Service
public class SecurityService {

	private static final Logger LOG = LoggerFactory.getLogger(SecurityService.class);

	@Autowired
	private Environment env;
	@Autowired
	private UserDao userDao;

	/**
	 * Ищет пользователя приложения по его логину.
	 * @param login имя, под которым пользователь зашел в систему
	 * @return информация о пользователе, может вернуть null, если пользователь не найден
	 */
	public User findUserByLogin(String login) {
		return userDao.findByLogin(login);
	}

	/**
	 * Возвращает информацию о текущем пользователе
	 * @return пользователь
	 */
	public User getCurrentUser() {
		DynamicAuthentication auth = getDynamicAuthentication();
		if (auth != null) {
			return auth.getUser();
		}
		return null;
	}

	/**
	 * @return информация о вошедшем в систему пользователе
	 */
	private DynamicAuthentication getDynamicAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication != null && authentication instanceof DynamicAuthentication) {
			return (DynamicAuthentication) authentication;
		}
		return null;
	}

	/**
	 * Авторизация пользователя в системе
	 *
	 * @param login имя пользователя
	 * @param password пароль
	 */
	public void loginUser(String login, String password) {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = authenticate(login, password);
		context.setAuthentication(authentication);
	}

	/**
	 * Проверяет корректность пары логин-пароль, и если все ок, то возвращает объект-аутентификацию
	 *
	 * @param login
	 * @param password
	 * @return
	 */
	public Authentication authenticate(String login, String password) {
		try {
			if (Objects.isNull(login)) {
				throw new BadCredentialsException(getMessage("authentication.incorrect"));
			}
			// Подключение через API к серверу SD под указанной учетной записью - проверка пароля
			new SdClientBean(env.getProperty("sd_application_server"), login, password);
			User user = findUserByLogin(login);
			LOG.info(getMessage("authentication.success", user.getName(), user.getLogin())); // сообщаем об успешном входе в систему
			return new DynamicAuthentication(user, true);
		} catch (Exception e) {
			// сообщаем об ошибке входа в систему
			throw new BadCredentialsException(getMessage("authentication.fail",
					login, e.getClass().getSimpleName(), e.getMessage()));
		}
	}
}