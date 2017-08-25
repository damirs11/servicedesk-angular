package ru.it.sd.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

import java.util.Objects;

import static ru.it.sd.model.EntityType.*;

/**
 * Тип сообщения в чате
 *
 * @author quadrix
 * @since 01.05.2017
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ChatLineType implements Code {

	CHANGE_REGISTERED("managerComment", CHANGE, 281484032738115L),
	CHANGE_PREPARING("doerComment", CHANGE, 724041771L),
	CHANGE_ON_APPROVE("initiatorComment", CHANGE, 724041771L);

	/** Идентификатор типа */
	private Long id;
	/** Название типа */
	private String name;
	/** Тип сущности, к которой относится тип */
	private EntityType entityType;

	ChatLineType(String name, EntityType entityType, Long id) {
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
	 * Поиск типа по идентификатору
	 *
	 * @param id идентификатор
	 * @return статус
	 * @throws ServiceException если указан неправильный код
	 */
	public static ChatLineType get(Long id) {
		if (Objects.isNull(id)) {
			return null;
		}
		for (ChatLineType value : values()) {
			if (id.equals(value.getId())) {
				return value;
			}
		}
		throw new ServiceException(ResourceMessages.getMessage("error.not.found"));
	}

}