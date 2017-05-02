package com.aplana.sd.service;

import com.aplana.sd.dao.OrganizationDao;
import com.aplana.sd.exception.SecurityException;
import com.aplana.sd.model.Organization;
import com.aplana.sd.model.Role;
import com.aplana.sd.model.User;
import com.aplana.sd.model.Operation;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

	@Autowired
	private Environment env;
	@Autowired
	private OrganizationDao orgDao;
	/**
	 * Ищет пользователя приложения по его логину.
	 * @param login имя, под которым пользователь зашел в систему
	 * @return информация о пользователе, может вернуть null, если пользователь не найден
	 */
	public User findUser(String login) {
		//todo
		User user = new User();
		user.setId(Math.round(10 * Math.random()));
		user.setLogin(login);
		user.setName("John Smith " + user.getId());
		Role role = new Role();
		role.setName("ADMIN");
		List<Operation> grants = new ArrayList<>();
		grants.add(Operation.ALL);
		role.setOperations(grants);
		return user;
	}

	public User getUser(String login) {
		User user = findUser(login);
		if (user != null) return user;
		throw new SecurityException();
	}

	/**
	 * Возвращает информацию о текущем пользователе
	 * @return пользователь
	 */
	public User currentUser() {
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
			// Подключение через API к серверу SD под указанной учетной записью
			SdClientBean sdClient = new SdClientBean(env.getProperty("sd_application_server"), login, password);
			User user = findUser(login);

			Organization findById("281486668796143");

			/*user.setName(sdClient.username());
			IPerson p = sdClient.current_user_person();
			IAccount a = p.getAccount();
			IRole[] roles = a.getRoles();
			if (roles.length > 0) {
				for (IRole role : roles) {
					LOG.debug("### role = " + role.getName());
				}
			}*/
			LOG.info(getMessage("authentication.success", user.getName(), user.getLogin())); // сообщаем об успешном входе в систему
			return new DynamicAuthentication(user, true);
		} catch (Exception e) {
			// сообщаем об ошибке входа в систему
			LOG.info(getMessage("authentication.fail", login, e.getClass().getSimpleName(), e.getMessage()));
			throw new BadCredentialsException(e.getMessage());
		}
	}
}