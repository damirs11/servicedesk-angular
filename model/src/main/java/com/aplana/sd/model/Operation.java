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
 * @since 07.03.2017
 */
public enum Operation implements GrantedAuthority {

	CHANGE_CREATE("Созданий изменений"),
	CHANGE_READ("Просмотр изменений"),
	CHANGE_UPDATE("Редактирование изменений"),
	CHANGE_UPDATE_PREPARING("Редактирование изменений в статусе \"Подготовка\""),
	CHANGE_UPDATE_ON_APPROVE("Редактирование изменений в статусе \"На согласовании\""),
	CHANGE_UPDATE_APPROVE_COMPLETE("Редактирование изменений в статусе \"Согласование завершено\""),
	CHANGE_UPDATE_EXECUTING("Редактирование изменений в статусе \"Реализация\""),
	CHANGE_UPDATE_RESOLVED("Редактирование изменений в статусе \"Решено\""),
	CHANGE_UPDATE_CLOSED("Редактирование изменений в статусе \"Закрыто\"");

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