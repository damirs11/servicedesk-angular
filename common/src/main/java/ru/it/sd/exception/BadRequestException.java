package ru.it.sd.exception;

import java.text.MessageFormat;

/**
 * Исключение выкидывается, когда клиент передает неправильные параметры в запросе
 *
 * @author quadrix
 * @since 19.08.2016
 */
public class BadRequestException extends IllegalArgumentException{

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}