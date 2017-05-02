package com.aplana.sd.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Пользователь системы
 *
 * @author quadrix
 *         07.03.2017 2:24
 */
public class User implements Serializable{
    /** Идентификатор пользователя*/
    private long id;
    /** Имя для входа в систему. Например, "iivanov" */
    private String login;
    /** Имя для отображения. Например, "Иван Иванович"*/
    private String name;
    /** Назначенные роли */
    private List<Role> roles;
    /** Пол */
    private boolean gender;
    /** Email */
    private String email;
    /** Должность */
    private String jobTitle;
    /** Имя */
    private String firstName;
    /** Фамилия */
    private String lastName;
    /** Отчество */
    private String middleName;
    /** Организация */
    private Organization organization;

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return StringUtils.defaultString(login);
    }
}