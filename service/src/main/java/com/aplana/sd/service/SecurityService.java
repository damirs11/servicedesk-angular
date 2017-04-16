package com.aplana.sd.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.aplana.sd.exception.SecurityException;
import com.aplana.sd.model.AppRole;
import com.aplana.sd.model.AppUser;
import com.aplana.sd.model.Operation;

import java.util.*;

/**
 * Сервис для работы с информацией о текущих параметрах безопасности
 *
 * @author quadrix
 * 07.03.2017 2:30
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
}