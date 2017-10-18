package ru.it.sd.model;

import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

import java.util.Objects;

/**
 * Типы сущностей, с которыми работает система
 *
 * @author quadrix
 * @since 01.05.2017
 */
public enum EntityType implements Code {

	CHANGE("Изменение", Change.class.getSimpleName(), 724041768L),
	PROBLEM("Проблема"),
	CALL("Обращение"),
	WORKORDER("Наряд", Workorder.class.getSimpleName(), 556859410L),
	ITEM("Объект"),
	WORKGROUP("Рабочая группа", Workgroup.class.getSimpleName());

	/** Название */
	private String title;
	/** Псевдоним. Как правило, совпадает с названием соответствующего модельного класса */
	private String alias;
	/** Идентификатор */
	private Long id;

	EntityType(String title) {
		this.title = title;
	}

	EntityType(String title, String alias) {
		this.title = title;
		this.alias = alias;
	}

	EntityType(String title, String alias, Long id) {
		this(title, alias);
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getName() {
		return title;
	}

	@Override
	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Поиск типа сущности по идентификатору
	 *
	 * @param id идентификатор
	 * @return тип сущности
	 * @throws ServiceException если указан неправильный код
	 * @throws NullPointerException если идентификатор не определен
	 */
	public static EntityType get(Long id) {
		if (Objects.isNull(id)) {
			return null;
		}
		for (EntityType value : values()) {
			if (id.equals(value.getId())) {
				return value;
			}
		}
		throw new ServiceException(ResourceMessages.getMessage("error.not.found"));
	}
}