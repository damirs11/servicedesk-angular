package ru.datateh.sd.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.datateh.sd.exception.SecurityException;
import ru.datateh.sd.model.AppUser;

/**
 * @author quadrix
 *         07.03.2017 2:30
 */
@Service
public class SecurityService {

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
		appUser.setName("Иван Иванович " + appUser.getId());
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
		return getUser(getDynamicAuthentication().getPrincipal());
	}

	/**
	 * @return аутентификацию текущего пользователя DynamicAuthentication
	 */
	DynamicAuthentication getDynamicAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication != null && authentication instanceof DynamicAuthentication) {
			return (DynamicAuthentication) authentication;
		} else {
			throw new SecurityException("Текущий пользователь не задан");
		}
	}
}