package ru.it.sd.model;

/**
 * Тип ограничения на право доступа
 *
 * @author quadrix
 * @since 10.10.2017
 */
public enum GrantRule implements HasId{

	/** Действие запрещено (не указано) */
	NONE(-1),
	/** Правило действует на все экземпляры сущности */
	ALWAYS(2),
	/** Правило действует только на экземпляры, где указана группа пользователя в качестве исполнителя*/
	WORKGROUP(1),
	/** Правило действует только на экземпляры, где пользователь указан как исполнитель */
	EXECUTOR(0);

	GrantRule() {
	}

	GrantRule(int order) {
		this.order = order;
	}

	/**
	 * Возвращает правило в зависимости от указанных параметров. В БД данные флаги
	 * хранятся раздельно. Данный метод производит анализ их значений.
	 */
	public static GrantRule get(Boolean always, Boolean executor, Boolean workgroup) {
		if (executor) {
			return EXECUTOR;
		}
		if (workgroup) {
			return WORKGROUP;
		}
		if (always) {
			return ALWAYS;
		}
		return NONE;
	}
	private int order = Integer.MIN_VALUE;

	public int getOrder() {
		return order;
	}

	@Override
	public Long getId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setId(Long id) {
		throw new UnsupportedOperationException();
	}

	static class Deserializer extends EnumJsonDeserializer<GrantRule> {
	}
}