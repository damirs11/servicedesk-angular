package com.aplana.sd.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;
import static com.aplana.sd.model.Operation.*;

/**
 * Роль приложения. Используется при определении прав доступа на выполнение бизнес-операций.
 *
 * @author quadrix
 *         07.03.2017 2:43
 */
public enum Role implements GrantedAuthority {

	CHANGE_INITIATOR("Изменения инициатор", new Operation[]{
			CHANGE_READ}),
	CHANGE_MANAGER("Изменения инициатор"),
	CHANGE_APPROVER("Изменения согласующий"),
	CHANGE_EXECUROT("Изменения исполнитель"),
	CHANGE_SUPERVISOR("Изменения наблюдатель");

	/** Название роли */
	private String name;
	/** Список назначенных роли бизнес-операций */
	private List<Operation> operations;

	Role(String name) {
		this.name = name;
	}

	Role(String name, Operation... operations) {
		this.name = name;
		this.operations = Arrays.asList(operations);
	}

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