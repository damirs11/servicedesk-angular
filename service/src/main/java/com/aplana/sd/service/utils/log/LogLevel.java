package com.aplana.sd.service.utils.log;

/**
 * Уровни критичности сообщений в журнале
 *
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a>
 * @since 20.10.2016 21:09
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