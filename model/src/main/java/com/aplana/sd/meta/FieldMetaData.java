package com.aplana.sd.meta;

import com.aplana.sd.exception.AppException;

import java.util.Comparator;
import java.util.Objects;

/**
 * Это не персистентный класс. Это класс, описывающий метаданные свойств.
 *
 * @author Vitalii Samolovskikh
 */
public class FieldMetaData implements Comparable<FieldMetaData>, Cloneable {
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
     * Название поля в таблице.
     */
    private String title;
    /**
     * ширина столбца таблицы по умолчанию
     */
    private int width = 100;
    /**
     * Порядковый номер столбца в таблице по умолчанию
     */
    private int order = 0;
    /**
     * Видимо или не видимое поле
     */
    private boolean visible = true;
    /**
     * Форма поля. Например, для дат сможем задавать отображение либо как обычно "31.12.2016", либо "декабрь 2016"
     * и т.д. Для каждого типа поля может потребоваться свой формат. Для чисел, это может быть денежный формат -
     * "1 000р" и т.д;
     */
    private String format = "";
    /**
     * точность, количество знаков после запятой, не более 19. Тип "Целое число". Обязательный атрибут для чисел.
     * Используется для редактирования и отображения в таблицах;
     */
    private int precision = 0;
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

    public String getTitle() {
        if (title != null) {
            return title;
        } else {
            return name;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = String.class.equals(type) && maxLength != null && maxLength > -1 ? maxLength : null;
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

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = String.class.equals(type) && minLength != null &&minLength > -1 ? minLength : null;
	}

	public Long getMax() {
		return max;
	}

	public void setMax(Long max) {
		this.max = isNumberClass() ? max : null;
	}

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
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

    public int getUnique() {
        return unique;
    }

    public void setUnique(int unique) {
        this.unique = unique;
    }

    public Long getMin() {
		return min;
	}

	public void setMin(Long min) {
		this.min = isNumberClass() ? min : null;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
	    FieldMetaData that = (FieldMetaData) o;
		return width == that.width &&
				order == that.order &&
				visible == that.visible &&
				precision == that.precision &&
				required == that.required &&
				readOnly == that.readOnly &&
                unique == that.unique &&
				maxLength == that.maxLength &&
				minLength == that.minLength &&
				max == that.max &&
				min == that.min &&
				Objects.equals(name, that.name) &&
				Objects.equals(type, that.type) &&
				Objects.equals(columnName, that.columnName) &&
				Objects.equals(title, that.title) &&
				Objects.equals(format, that.format) &&
				Objects.equals(pattern, that.pattern);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, order);
	}

	@Override
    public int compareTo(FieldMetaData o) {
        return this.getOrder() - o.getOrder();
    }

    /**
     * В базовой реалиации - protected, а нам нужно public
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AppException("Error while cloning the metadata", e);
        }
        return obj;
    }

    /**
     * Класс для сортировки метаданных в по порядку order.
     * Вынесено в отдельный класс, поскольку понадобилось два обработчика метаданных.
     */
    public static class ComparatorByOrder implements Comparator<FieldMetaData> {
        @Override
        public int compare(FieldMetaData o1, FieldMetaData o2) {
            return o1.getOrder() - o2.getOrder();
        }
    }
}