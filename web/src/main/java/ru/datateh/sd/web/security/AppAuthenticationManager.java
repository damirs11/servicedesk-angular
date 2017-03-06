package ru.datateh.sd.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.datateh.sd.service.DynamicAuthentication;
import ru.datateh.sd.service.SecurityService;
import ru.datateh.sd.model.AppUser;

/**
 * Сервис аутентификации пользователя
 *
 * @author quadrix
 *
 */
@Service
public class AppAuthenticationManager implements AuthenticationManager {
	private static final Logger LOG = LoggerFactory.getLogger(AppAuthenticationManager.class);

	@Autowired
	private SecurityService securityService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			String login = (String) authentication.getPrincipal();
			LOG.debug("Try authenticate \"{}\"", login);
			AppUser user = securityService.findUser(login);
			LOG.info("User \"{}\" was authenticated", user.getName()); // сообщаем об успешном входе в систему
			return new DynamicAuthentication(user, true);
		} catch (Exception e) {
			// сообщаем об ошибке входа в систему
			LOG.info("User cannot be authenticated. " + e.getClass().getSimpleName() + ": " + e.getMessage(), e);
			throw e;
		}
	}
}