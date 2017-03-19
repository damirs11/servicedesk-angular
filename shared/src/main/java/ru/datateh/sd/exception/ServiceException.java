package ru.datateh.sd.exception;

import java.text.MessageFormat;

/**
 * Экземпляры исключений данного класса должны содержать
 * понятные пользователю сообщения на русском языке.
 *
 * @author quadrix
 * 07.03.2016 1:57
 */
public class ServiceException extends AppException {

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}