package com.aplana.sd.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.aplana.sd.model.Operation.*;

/**
 * Роль приложения. Используется при определении прав доступа на выполнение бизнес-операций.
 *
 * @author quadrix
 * @since 07.03.2017
 */
public enum Role implements GrantedAuthority {

	CHANGE_INITIATOR("Изменения инициатор", 281494881712586L, new Operation[]{
			CHANGE_READ}),
	CHANGE_MANAGER("Изменения менеджер", 281494847699690L),
	CHANGE_APPROVER("Изменения согласующий", 281494881711474L),
	CHANGE_EXECUROT("Изменения исполнитель", 281494881711997L),
	CHANGE_SUPERVISOR("Изменения наблюдатель", 281495234950996L);

	/** Название роли */
	private String name;
	/** Идентифиикатор роли */
	private Long id;
	/** Список назначенных роли бизнес-операций */
	private List<Operation> operations;

	Role(String name, Long id) {
		this.name = name;
		this.id = id;
	}

	Role(String name, Long id, Operation... operations) {
		this.name = name;
		this.id = id;
		this.operations = Arrays.asList(operations);
	}

	public String getName() {
		return name;
	}

	@Override
	public String getAuthority() {
		return "ROLE_" + name;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	/**
	 * Поиск роли по её идентификатору
	 * @return роль, либо null, если указанного идентификатора нет
	 */
	public static Role getById(Long id) {
		Objects.requireNonNull(id);
		for(Role role : Role.values()) {
			if (id.equals(role.id)) {
				return role;
			}
		}
		return null;
	}

}