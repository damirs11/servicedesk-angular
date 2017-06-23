package ru.it.sd.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

import java.util.Objects;

import static ru.it.sd.model.EntityType.*;

/**
 * Статусы сущностей
 *
 * @author quadrix
 * @since 01.05.2017
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EntityStatus implements Code {

	CHANGE_REGISTERED("Зарегистрировано", CHANGE, 3095134325L),
	CHANGE_PREPARING("Подготовка", CHANGE, 3095134326L),
	CHANGE_ON_APPROVE("На согласовании", CHANGE, 3095134327L),
	CHANGE_APPROVE_COMPLETE("Согласование завершено", CHANGE, 3095134328L),
	CHANGE_EXECUTING("Реализация", CHANGE, 3095134329L),
	CHANGE_RESOLVED("Решено", CHANGE, 3095134330L),
	CHANGE_CLOSED("Закрыто", CHANGE, 3095134331L),

	CALL_REGISTERED("Зарегистрировано", CALL, 3094610092L),
	CALL_TO_ENGINEER("Направлено инженеру", CALL, 281478327763609L),
	CALL_EXECUTING("В работе инженер", CALL, 281478336938148L),
	CALL_RESOLVED("Решено", CALL, 3094610094L),
	CALL_CLOSED("Закрыто", CALL, 3094610096L),

	WORKORDER_OPEN("Открыт", WORKORDER, 281478237061820L),
	WORKORDER_COMPLETE("Выполнен", WORKORDER, 3095134439L),
	WORKORDER_CLOSED("Закрыт", WORKORDER, 281478237061818L),

	ITEM_ONSTOCK("На складе", ITEM, 3094610398L),
	ITEM_INWORK("В эксплуатации", ITEM, 3094610404L),
	ITEM_REPAIRING("Ремонтируется", ITEM, 3094610405L),
	ITEM_WRITENOFF("Списано", ITEM, 3094610406L),
	ITEM_LOST("Потеряно", ITEM, 3094610408L);

	/** Идентификатор статуса */
	private Long id;
	/** Название статуса */
	private String name;
	/** Тип сущности, к которой относится статус */
	private EntityType entityType;

	EntityStatus(String name, EntityType entityType, Long id) {
		this.name = name;
		this.entityType = entityType;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	@Override
	public void setId(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Поиск статуса по идентификатору
	 *
	 * @param id идентификатор
	 * @return статус
	 * @throws ServiceException если указан неправильный код
	 */
	public static EntityStatus get(Long id) {
		if (Objects.isNull(id)) {
			return null;
		}
		for (EntityStatus value : values()) {
			if (id.equals(value.getId())) {
				return value;
			}
		}
		throw new ServiceException(ResourceMessages.getMessage("error.not.found"));
	}

}