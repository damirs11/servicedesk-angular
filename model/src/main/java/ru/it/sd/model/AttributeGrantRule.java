package ru.it.sd.model;

import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

/**
 * Тип ограничения на право доступа атрибутов
 *
 * @author nsychev
 * @since 29.01.2018
 */
public enum AttributeGrantRule implements HasId {

	HIDE(0L),
	READ(1L),
	UPDATE(2L);


	private Long id;

	AttributeGrantRule(Long id){
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		throw new UnsupportedOperationException();
	}

	public static AttributeGrantRule getById(Long id){
		if (id == null) {
			return null;
		}
		for (AttributeGrantRule value : values()) {
			if (id.equals(value.getId())) {
				return value;
			}
		}
		throw new ServiceException(ResourceMessages.getMessage("error.not.found"));
	}

	static class Deserializer extends EnumJsonDeserializer<AttributeGrantRule> {
	}

}