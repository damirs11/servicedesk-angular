package ru.it.sd.service;

import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.it.sd.model.Role;
import ru.it.sd.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Динамическая аутентификация используется, т.к. пользователь у нас может менять свою роль и будут в соответствие с
 * этим меняться его права.
 *
 * @author quadrix
 * @since 07.03.2017
 */
public class DynamicAuthentication implements Authentication {
    /** Аутентифицированный пользователь */
    private final User user;
    /** Указывает на то, что аутентификация пройдена. */
    private boolean authenticated;
    /** Объект для взаимодействия с API HP SD*/
    private SdClientBean sdClient;
    /** WebAccount, клиент для пользователей, которые используют регистрационные имена */
    private SdClientBean webAccount;

    public DynamicAuthentication(User user, boolean authenticated, SdClientBean sdClient, SdClientBean webAccount) {
        this.user = user;
        this.authenticated = authenticated;
        this.sdClient = sdClient;
        this.webAccount = webAccount;
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
    public void setAuthenticated(boolean isAuthenticated) {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return user.getName();
    }

    public User getUser() {
        return user;
    }

    public SdClientBean getSdClient() {
        return sdClient;
    }

    public SdClientBean getActualSdClient() {
        return sdClient.is_specialist() ? sdClient : webAccount;
    }
}