package com.aplana.sd.service.utils.log;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * Журнал сообщений в рамках одного запроса пользователя
 *
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a>
 * @since 20.10.2016 20:42
 */
@Service
@Scope("request")
public class RequestLog {

	/** Хранилище сообщений */
	private List<LogRecord> records = new ArrayList<>();

	@PreDestroy
	private void destroy() {
		records.clear();
	}

	public void debug(String msg) {
		records.add(new LogRecord(LogLevel.DEBUG, msg));
	}

	public void info(String msg) {
		records.add(new LogRecord(LogLevel.INFO, msg));
	}

	public void warn(String msg) {
		records.add(new LogRecord(LogLevel.WARN, msg));
	}

	public void error(String msg) {
		records.add(new LogRecord(LogLevel.ERROR, msg));
	}

	/**
	 * Возвращает информацию о наличии ошибок в журнале
	 * @return true - есть ошибки; false - ошибок нет
	 */
	public boolean hasError() {
		for (LogRecord record : records) {
			if (LogLevel.ERROR == record.getLevel()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (LogRecord record : records) {
			sb.append(record.toString());
			sb.append('\n');
		}
		return sb.toString();
	}

}