package com.aplana.sd.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import com.aplana.sd.model.Role;
import com.aplana.sd.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Динамическая аутентификация используется, т.к. пользователь у нас может менять свою роль и будут в соответствие с
 * этим меняться его права.
 *
 * @author quadrix
 * 07.03.2017 3:07
 */
public class DynamicAuthentication implements Authentication {
    /** Аутентифицированный пользователь */
    private final User user;
    /** Указывает на то, что аутентификация пройдена. */
    private boolean authenticated;

    public DynamicAuthentication(User user, boolean authenticated) {
        this.user = user;
		this.authenticated = authenticated;
    }

    /**
     * Возвращает текущую роль пользователя, плюс все доступные операции
     */
    @Override
    public synchronized Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Set<GrantedAuthority> authorities = new HashSet<>();
			for (Role role : user.getRoles()) {
				authorities.add(role);
				authorities.addAll(role.getOperations());
			}
            return authorities;
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * Не используется.
     */
    @Override
    public Object getCredentials() {
        throw new UnsupportedOperationException("DynamicAuthentication.getCredentials()");
    }
	/**
	 * Не используется.
	 */
    @Override
    public Object getDetails() {
        return null;
    }

    /**
     * Возвращает текущего пользователя.
     */
    @Override
    public String getPrincipal() {
        return user.getLogin();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return user.getName();
    }
}