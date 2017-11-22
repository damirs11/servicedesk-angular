package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Пользователь системы
 *
 * @author quadrix
 * @since 07.03.2017
 */
@ClassMeta(tableName = "rep_accounts")
public class User implements Serializable {

	private static final long serialVersionUID = 7956825709843649122L;

	/**
	 * Идентификатор пользователя
	 */
	@FieldMeta(columnName = "acc_oid", key = true)
	private Long id;
	/**
	 * Имя для входа в систему. Например, "iivanov"
	 */
	@FieldMeta(columnName = "acc_loginname")
	private String login;
	/**
	 * Имя для отображения. Например, "Иван Иванович Иванов"
	 */
	@FieldMeta(columnName = "acc_showname")
	private String name;
	/**
	 * Назначенные роли
	 */
	private List<Role> roles = new ArrayList<>();
	/**
	 * Дополнительная информация о пользователе
	 */
	private Person person;

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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return StringUtils.defaultString(login);
	}
}