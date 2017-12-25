package ru.it.sd.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

import java.util.Objects;

/**
 * Приоритеты сущностей
 *
 * @author quadrix
 * @since 03.05.2017
 */
@Deprecated
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = EntityPriority2.Deserializer.class)
public enum EntityPriority2 implements Code {

	UNKNOWN("Нет", 3094610020L),
	MINOR("Низкий", 3094610021L),
	MAJOR("Средний", 3094610022L),
	CRITICAL("Высокий", 3094610023L),
	BLOCKER("Высший", 3094610024L);

	/** Идентификатор статуса */
	private Long id;
	/** Название статуса */
	private String name;

	EntityPriority2(String name, Long id) {
		this.name = name;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		throw new UnsupportedOperationException();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Поиск приоритета по идентификатору
	 *
	 * @param id идентификатор
	 * @return приоритет
	 * @throws ServiceException если указан неправильный код
	 * @throws NullPointerException если идентификатор не определен
	 */
	public static EntityPriority2 get(Long id) {
		if (Objects.isNull(id)) {
			return null;
		}
		for (EntityPriority2 value : values()) {
			if (id.equals(value.getId())) {
				return value;
			}
		}
		throw new ServiceException(ResourceMessages.getMessage("error.not.found"));
	}

	static class Deserializer extends EnumJsonDeserializer<EntityPriority2> {}
}