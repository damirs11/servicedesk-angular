package com.aplana.sd.model;

/**
 * Приоритеты сущностей
 *
 * @author quadrix
 * @since 03.05.2017
 */
public enum EntityPriority implements Code {

	UNKNOWN("Нет", 3094610020L),
	MINOR("Низкий", 3094610021L),
	MAJOR("Средний", 3094610022L),
	CRITICAL("Высокий", 3094610023L),
	BLOCKER("Высший", 3094610024L);

	/** Идентификатор статуса */
	private Long id;
	/** Название статуса */
	private String name;

	EntityPriority(String name, Long id) {
		this.name = name;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}