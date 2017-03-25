package ru.datateh.sd.exception;

import java.text.MessageFormat;

/**
 * Базовый класс для всех исключений приложения.
 * Объявлен абстрактным, чтобы не создавать его экземпляры напрямую.
 *
 * @author quadrix
 * 07.03.2016 1:57
 */
public abstract class AppException extends RuntimeException {

    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}