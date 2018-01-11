package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;
import ru.it.sd.util.ResourceMessages;

import java.io.Serializable;

/**
 * Базовый класс для кодов
 *
 * @author quadrix
 * @since 15.11.2017
 */
@ClassMeta(tableName = "code")
public class BaseCode implements Code, Serializable {

	private static final long serialVersionUID = -5516364159201226727L;
	/**
	 * Идентификатор
	 */
	@FieldMeta(columnName = "id", key = true)
	private Long id;

	/**
	 * Название
	 */
	@FieldMeta(columnName = "name")
	private String name;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, AppToStringStyle.getInstance());
	}

	public <T extends Code> T convertTo(Class<T> toClass){
		try {
			T to = toClass.newInstance();
			to.setId(id);
			to.setName(name);
			return to;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ServiceException(ResourceMessages.getMessage("error.instantiation", toClass.getName()));
		}
	}
}