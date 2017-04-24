package com.aplana.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.aplana.sd.exception.SecurityException;
import com.aplana.sd.model.AppRole;
import com.aplana.sd.model.AppUser;
import com.aplana.sd.model.Operation;

import java.util.*;

import static com.aplana.sd.util.ResourceMessages.getMessage;

/**
 * Сервис для работы с информацией о текущих параметрах безопасности
 *
 * @author quadrix
 * 07.03.2017 2:30
 */
@Service
public class SecurityService {

	private static final Logger LOG = LoggerFactory.getLogger(SecurityService.class);
	/**
	 * Ищет пользователя приложения по его логину.
	 * @param login имя, под которым пользователь зашел в систему
	 * @return информация о пользователе, может вернуть null, если пользователь не найден
	 */
	public AppUser findUser(String login) {
		//todo
		AppUser appUser = new AppUser();
		appUser.setId(Math.round(10 * Math.random()));
		appUser.setLogin(login);
		appUser.setName("John Smith " + appUser.getId());
		AppRole appRole = new AppRole();
		appRole.setName("ADMIN");
		List<Operation> grants = new ArrayList<>();
		grants.add(Operation.ALL);
		appRole.setOperations(grants);
		return appUser;
	}

	public AppUser getUser(String login) {
		AppUser user = findUser(login);
		if (user != null) return user;
		throw new SecurityException();
	}

	/**
	 * Возвращает информацию о текущем пользователе
	 * @return пользователь
	 */
	public AppUser currentUser() {
		DynamicAuthentication auth = getDynamicAuthentication();
		if (auth != null) {
			return getUser(auth.getPrincipal());
		}
		return null;
	}

	/**
	 * @return аутентификацию текущего пользователя DynamicAuthentication
	 */
	DynamicAuthentication getDynamicAuthentication() {
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
	 * Проверяет корректность пары логин-пароль, и если все ок, то возвращает объект-аутентикация
	 * @param login
	 * @param password
	 * @return
	 */
	public Authentication authenticate(String login, String password) {
		try {
			if (Objects.isNull(login)) {
				throw new BadCredentialsException(getMessage("authentication.incorrect"));
			}
			AppUser user = findUser(login);
			if (!login.equals(password)) {//todo временная проверка
				throw new BadCredentialsException(getMessage("authentication.incorrect"));
			}
			LOG.info(getMessage("authentication.success", user.getName(), user.getLogin())); // сообщаем об успешном входе в систему
			return new DynamicAuthentication(user, true);
		} catch (Exception e) {
			// сообщаем об ошибке входа в систему
			LOG.info(getMessage("authentication.fail", login, e.getClass().getSimpleName(), e.getMessage()));
			throw e;
		}
	}
}