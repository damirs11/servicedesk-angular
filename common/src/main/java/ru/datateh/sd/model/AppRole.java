package ru.datateh.sd.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * Роль приложения. Используется при определении прав доступа на выполнение бизнес-операций.
 *
 * @author quadrix
 *         07.03.2017 2:43
 */
public class AppRole implements GrantedAuthority {

	/** Название роли */
	private String name;
	/** Список назначенных роли бизнес-операций */
	private List<Operation> operations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return "ROLE_" + name;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
}