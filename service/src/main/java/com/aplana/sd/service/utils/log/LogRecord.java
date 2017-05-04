package com.aplana.sd.service.utils.log;

import java.text.MessageFormat;
import java.util.Date;

/**
 * Модельный класс для одной записи журнала
 *
 * @author quadrix
 * @since 20.10.2016
 */
public class LogRecord {
	/** Формат сообщений в журнале*/
	private static final String LOG_RECORD_FORMAT = "{0, date, short} {0, time, full}: {1}: {2}";
	/** Дата сообщения */
	private Date date;
	/** Крититичность сообщения */
	private LogLevel level;
	/** Текст сообщения */
	private String message;

	public LogRecord(LogLevel level, String message) {
		this.date = new Date();
		this.level = level;
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public LogLevel getLevel() {
		return level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return MessageFormat.format(LOG_RECORD_FORMAT, date, level, message);
	}
}