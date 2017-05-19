package ru.it.sd.model;

import static ru.it.sd.model.EntityType.*;

/**
 * Категории сущностей
 */
public enum EntityCategory implements Code {

	WORKORDER_TASK("Задача", WORKORDER, 3095134393L),
	WORKORDER_CHANGE("Изменения", WORKORDER, 3095134397L),
	WORKORDER_APPROVE("Согласование", WORKORDER, 3095134401L),
	WORKORDER_VIDEO("Видеонаблюдение", WORKORDER, 281492964966928L),
	WORKORDER_CLAIM("Претензия", WORKORDER, 281494929802849L),
	WORKORDER_PLANNING("Планирование", WORKORDER, 281495316004885L);

	/** Идентификатор категории */
	private Long id;
	/** Название категории */
	private String name;
	/** Тип сущности, к которой относится категория */
	private EntityType entityType;

	EntityCategory(String name, EntityType entityType, Long id) {
		this.name = name;
		this.entityType = entityType;
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

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}
}