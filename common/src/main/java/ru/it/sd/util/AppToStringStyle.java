package ru.it.sd.util;

import org.apache.commons.lang3.builder.ToStringStyle;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Стиль для формирования строк из объектов
 *
 * @author quadrix
 * @since 22.07.2016 16:16
 */

public class AppToStringStyle extends ToStringStyle {

	private static final long serialVersionUID = 4193424031779774851L;

	private static final String DATETIME_FORMAT_PATTERN = "dd.MM.yyyy HH:mm:ss";
	private static AppToStringStyle INSTANCE = new AppToStringStyle();
	/**
	 * Потому что класс SimpleDateFormat непотокобезопасный
	 */
	private static ThreadLocal<SimpleDateFormat> SimpleDateFormatHolder = new ThreadLocal<SimpleDateFormat>() {
		public SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
		}
	};

	private AppToStringStyle() {
		super();
		this.setUseShortClassName(true);
		this.setUseIdentityHashCode(false);
	}

	public static AppToStringStyle getInstance() {
		return INSTANCE;
	}

	protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
		Object result = value;
		if (value instanceof Date) {
			result = SimpleDateFormatHolder.get().format(value);
		}
		super.appendDetail(buffer, fieldName, result);
	}
}