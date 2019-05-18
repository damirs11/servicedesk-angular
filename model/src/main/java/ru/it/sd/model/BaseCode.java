package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;
import ru.it.sd.util.ResourceMessages;

import java.io.Serializable;
import java.util.Objects;

/**
 * Базовый класс для кодов
 *
 * @author quadrix
 * @since 15.11.2017
 */
@ClassMeta(tableName = "code")
public class BaseCode implements Code, Serializable {

	private static final long serialVersionUID = -5516364159201226727L;

	public BaseCode() {
	}

	public BaseCode(Long id) {
		this.id = id;
	}

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

    @FieldMeta(columnName = "ordering")
    private Integer order;

    @FieldMeta(columnName = "parentCode")
	private BaseCode parent;

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
    public Integer getOrder() {
        return order;
    }

	@Override
	public void setOrder(Integer order) {
		this.order = order;
	}

	public BaseCode getParent() {
		return parent;
	}

	public void setParent(BaseCode parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, AppToStringStyle.getInstance());
	}

	public <T extends BaseCode> T convertTo(Class<T> toClass){
		try {
			T to = toClass.newInstance();
			to.setId(id);
			to.setName(name);
			to.setOrder(order);
			to.setParent(parent);
			return to;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ServiceException(ResourceMessages.getMessage("error.instantiation", toClass.getName()));
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return  false;
		if (!(o instanceof BaseCode)) return false;
		BaseCode baseCode = (BaseCode) o;
		return Objects.equals(id, baseCode.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}