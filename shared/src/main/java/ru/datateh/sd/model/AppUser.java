package ru.datateh.sd.model;

import java.util.List;

/**
 * Пользователь системы
 *
 * @author quadrix
 *         07.03.2017 2:24
 */
public class AppUser {
    /** Идентификатор пользователя*/
    private long id;
    /** Имя для входа в систему. Например, "iivanov" */
    private String login;
    /** Имя для отображения. Например, "Иван Иванович"*/
    private String name;
    /** Назначенные пользователю роли */
    private List<AppRole> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AppRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AppRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "login='" + login + '\'' +
                '}';
    }
}