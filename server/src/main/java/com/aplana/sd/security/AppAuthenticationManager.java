package com.aplana.sd.security;

import com.aplana.sd.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
		String login = Objects.toString(authentication.getPrincipal());
		String password = Objects.toString(authentication.getCredentials());
		return securityService.authenticate(login, password);
	}
}