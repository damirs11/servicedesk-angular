package ru.it.sd.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

/**
 * Типы сущностей, с которыми работает система
 *
 * @author quadrix
 * @since 01.05.2017
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = EntityType.Deserializer.class)
public enum EntityType implements Code {

	CHANGE("Изменение", Change.class.getSimpleName(), 724041768L),
	PROBLEM("Проблема", null, 717488173L),
	CALL("Обращение", null, 563019801L),
	WORKORDER("Наряд", Workorder.class.getSimpleName(), 556859410L),
	ITEM("Объект", ConfigurationItem.class.getSimpleName(), 796000260L),
	WORKGROUP("Рабочая группа", Workgroup.class.getSimpleName()),
	APPROVAL("Согласование", Approval.class.getSimpleName(), 281478244794382L),
	ASSIGNMENT("Назначение", Assignment.class.getSimpleName(), 665649174L),
	PERSON("Персона",Person.class.getSimpleName(), 555155484L);

	/**
	 * Название
	 */
	private String title;
	/**
	 * Псевдоним. Как правило, совпадает с названием соответствующего модельного класса
	 */
	private String alias;
	/**
	 * Идентификатор
	 */
	private Long id;
	/**
	 * Порядок
	 */
	private Integer order;

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
	@Override
	public Integer getOrder(){
		return order;
	}


	@Override
	public void setOrder(Integer order){
		throw new UnsupportedOperationException();
	}

	/**
	 * Поиск типа сущности по идентификатору
	 *
	 * @param id идентификатор
	 * @return тип сущности
	 * @throws ServiceException     если указан неправильный код
	 * @throws NullPointerException если идентификатор не определен
	 */
	public static EntityType get(Long id) {
		if (id == null) {
			return null;
		}
		for (EntityType value : values()) {
			if (id.equals(value.getId())) {
				return value;
			}
		}
		throw new ServiceException(ResourceMessages.getMessage("error.not.found"));
	}

	/**
	 * Получаем тип сущности по классу
	 *
	 * @param clazz класс сущности
	 * @return тип сущности
	 */
	public static EntityType getByClass(Class clazz) {
		if (clazz == null) {
			return null;
		}
		for (EntityType value : values()) {
			if (clazz.getSimpleName().equals(value.getAlias())) {
				return value;
			}
		}
		throw new ServiceException(ResourceMessages.getMessage("error.not.found"));
	}

	static class Deserializer extends EnumJsonDeserializer<EntityType> {
	}
}