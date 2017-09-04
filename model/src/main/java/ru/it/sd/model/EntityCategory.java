package ru.it.sd.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Objects;

import static ru.it.sd.model.EntityType.*;

/**
 * Категории сущностей
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EntityCategory implements Code {

	WORKORDER_TASK("Задача", WORKORDER, 3095134393L),
	WORKORDER_CHANGE("Изменения", WORKORDER, 3095134397L),
	WORKORDER_APPROVE("Согласование", WORKORDER, 3095134401L),
	WORKORDER_VIDEO("Видеонаблюдение", WORKORDER, 281492964966928L),
	WORKORDER_CLAIM("Претензия", WORKORDER, 281494929802849L),
	WORKORDER_PLANNING("Планирование", WORKORDER, 281495316004885L),

	ITEM_BUSINESS_PC("Business PC", ITEM, 3094610482L),
	ITEM_CAMERA("Camera", ITEM, 3095396897L),
	ITEM_COMMUNICATION("Communication", ITEM, 3095396837L),
	ITEM_DOCUMENTATION("Documentation", ITEM, 3095396981L),
	ITEM_HARDWARE("Hardware", ITEM, 3094610480L),
	ITEM_LAPTOP("Laptop", ITEM, 3094610493L),
	ITEM_MAINFRAME("Mainframe", ITEM, 3095396359L),
	ITEM_MODEM("Modem", ITEM, 3095396757L),
	ITEM_MONITOR("Monitor", ITEM, 3095396404L),
	ITEM_OFFICE_EQUIPMENT("Office Equipment", ITEM, 3095396372L),
	ITEM_OTHER("Other", ITEM, 3095396374L),
	ITEM_PHONE("Phone", ITEM, 3095396838L),
	ITEM_ROUTER("Router", ITEM, 3095396751L),
	ITEM_SERVER("Server", ITEM, 3095396357L),
	ITEM_SOFTWARE("Software", ITEM, 3095396905L),
	ITEM_STORAGE("Storage", ITEM, 3095396428L),
	ITEM_SWITCH("Switch", ITEM, 3095396754L),
	ITEM_SYSTEM("System", ITEM, 3094610481L),
	ITEM_TELEVISION("Television", ITEM, 3095396874L),
	ITEM_UPS("UPS", ITEM, 281478315704459L),
	ITEM_VIDEO("Video", ITEM, 3095396873L),
	ITEM_WORKSTATION("Workstation", ITEM, 3095396356L),

	CHANGE_ITEC("ITEC", CHANGE, 3095397034L),
	CHANGE_CAB("CAB", CHANGE, 3095397038L),
	CHANGE_MANAGER("Manager", CHANGE, 3095397039L),
	CHANGE_PREAUTHORIZED("Стандартное изменение", CHANGE, 3095397040L),
	CHANGE_UNPLANNED("Внеплановое", CHANGE, 3120234526L),
	CHANGE_NORMAL("Нормальное изменение", CHANGE, 281487774265804L),
	CHANGE_ESSENTIAL("Существенное изменение", CHANGE, 281487774265807L),
	CHANGE_MINIMAL("Минимальное", CHANGE, 281487774265811L),
	CHANGE_EMERGENCY("Экстренное изменение", CHANGE, 281494871474972L);

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

	/**
	 * Поиск категорию по её идентификатору
	 * @return категория, либо null, если указанного идентификатора нет
	 */
	public static EntityCategory getById(Long id) {
		Objects.requireNonNull(id);
		for(EntityCategory category : EntityCategory.values()) {
			if (id.equals(category.id)) {
				return category;
			}
		}
		return null;
	}
}