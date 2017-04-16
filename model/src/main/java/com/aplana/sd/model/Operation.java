package com.aplana.sd.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Бизнес-операция. Используется при определении прав доступа пользователя.
 *
 * <p>В данном списке описаны все действия, которые подлежат разграничению
 * по ролевой структуре. Т.к. количество операций в системе конечное и операции жестко привязаны к коду,
 * используется enum. При назначении прав доступа следует использовать эти операции.</p>
 *
 * <p>Если мы храним операции в БД как int, то нельзя менять порядок операций в этом enum.
 * Если мы храним операции в БД как строки, то нельзя их переименовывать.</p>
 *
 * @author quadrix
 *         07.03.2017 2:51
 */
public enum Operation implements GrantedAuthority {

	ALL("Все права доступа");

	/** Код бизнес-операции*/
	private String title;

	Operation(String title) {
		this.title = title;
	}

	@Override
	public String getAuthority() {
		return "OPER_" + name();
	}
}