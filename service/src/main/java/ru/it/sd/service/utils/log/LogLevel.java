package ru.it.sd.service.utils.log;

/**
 * Уровни критичности сообщений в журнале
 *
 * @author quadrix
 * @since 20.10.2016
 */
public enum LogLevel {

	DEBUG("Отладка"),
	INFO("Сообщение"),
	WARN("Предупреждение"),
	ERROR("Ошибка");

	private String title;

	LogLevel(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return title;
	}
}