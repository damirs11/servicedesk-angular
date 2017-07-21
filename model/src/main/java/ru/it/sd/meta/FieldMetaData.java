package ru.it.sd.meta;

import ru.it.sd.exception.AppException;

/**
 * Это не персистентный класс. Это класс, описывающий метаданные свойств.
 *
 * @author Vitalii Samolovskikh
 */
public class FieldMetaData implements Cloneable {

	/**
	 * Информация получена из аннотации или напрямую из поля?
	 */
	boolean annotation = false;
	/**
	 * Название свойства
	 */
	private String name;
	/**
	 * Класс свойства
	 */
	private Class<?> type;
	/**
	 * Название столбца в бд
	 */
	private String columnName;
	/**
	 * Название столбца в бд
	 */
	private String tableAlias;
	/**
	 * Максимальное количество символов в строке.
	 */
	private int maxLength = Integer.MAX_VALUE;
	/**
	 * Минимальное количество символов в строке.
	 */
	private int minLength = 0;
	/**
	 * Максимальное значение числа.
	 */
	private long max = Long.MAX_VALUE;
	/**
	 * Минимальное значение числа.
	 */
	private long min = Long.MIN_VALUE;
	/**
	 * Является ли поле обязательным при заполнении.
	 */
	private boolean required = false;
	/**
	 * Поле только для чтения.
	 */
	private boolean readOnly = false;
	/**
	 * Является ли значение поле уникальным в пределах системы.
	 */
	private int unique = 0;
	/**
	 * Regex выражение для проверки значения поля на клиенте
	 */
	private String pattern = "";

	public boolean isAnnotation() {
		return annotation;
	}

	public void setAnnotation(boolean annotation) {
		this.annotation = annotation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}

	public long getMin() {
		return min;
	}

	public void setMin(long min) {
		this.min = min;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public int getUnique() {
		return unique;
	}

	public void setUnique(int unique) {
		this.unique = unique;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	private boolean isNumberClass() {
		return int.class.equals(type) ||
				long.class.equals(type) ||
				byte.class.equals(type) ||
				short.class.equals(type) ||
				float.class.equals(type) ||
				double.class.equals(type) ||
				Number.class.isAssignableFrom(type);
	}

	/**
	 * В базовой реалиации - protected, а нам нужно public
	 *
	 * @return
	 * @throws CloneNotSupportedException
	 */
	@Override
	public Object clone() {
		Object obj;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AppException("Error while cloning the metadata", e);
		}
		return obj;
	}
}