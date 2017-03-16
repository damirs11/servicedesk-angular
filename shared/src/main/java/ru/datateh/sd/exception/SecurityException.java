package ru.datateh.sd.exception;

import java.text.MessageFormat;

/**
 * Исключительная ситуация для ошибок прав доступа
 *
 * @author quadrix
 *         07.03.2017 3:14
 */
public class SecurityException extends AppException {

	public SecurityException() {
		super();
	}

	public SecurityException(String message) {
		super(message);
	}

	public SecurityException(String message, String[] args) {
		super(MessageFormat.format(message, args));
	}

	public SecurityException(String message, Throwable cause) {
		super(message, cause);
	}
}