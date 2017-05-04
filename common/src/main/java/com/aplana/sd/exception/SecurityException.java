package com.aplana.sd.exception;

import java.text.MessageFormat;

/**
 * Исключительная ситуация для ошибок прав доступа
 *
 * @author quadrix
 * @since 07.03.2017
 */
public class SecurityException extends AppException {

	public SecurityException() {
		super();
	}

	public SecurityException(String message) {
		super(message);
	}

	public SecurityException(String message, Object... args) {
		super(MessageFormat.format(message, args));
	}

	public SecurityException(String message, Throwable cause) {
		super(message, cause);
	}
}