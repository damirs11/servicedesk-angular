package ru.it.sd.exception;

import java.text.MessageFormat;

/**
 * Класс исключений для ошибок поиска объектов по указанным параметрам
 *
 * @author quadrix
 * @since 12.07.2017
 */
public class NotFoundException extends AppException {

    private static final long serialVersionUID = 4541940977441222163L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}