package ru.it.sd.exception;

import java.text.MessageFormat;

/**
 * Базовый класс для всех исключений приложения.
 * Объявлен абстрактным, чтобы не создавать его экземпляры напрямую.
 *
 * @author quadrix
 * @since 07.03.2016
 */
public class AppException extends RuntimeException {

    private static final long serialVersionUID = 5392652078420223578L;

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