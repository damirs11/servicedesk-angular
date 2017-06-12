package ru.it.sd.web.controller;

import ru.it.sd.exception.ClientMessage;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;
import com.fasterxml.jackson.core.JsonParseException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;

/**
 * Этот бин будет ловить все ошибки, выброшенные контроллерами и формировать правильный ответ клиенту
 *
 * @author quadrix
 * @since 24.06.2016
 */
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	/**
	 * Общий метод для обнаружения ошибок.
	 */
	@ExceptionHandler
	@ResponseStatus
	@ResponseBody
	private ClientMessage handleException(Exception ex) {
		LOG.error(ex.getMessage(), ex);
		return new ClientMessage(
				ResourceMessages.getMessage(Integer.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()).toString()),
				ExceptionUtils.getStackTrace(ex));
	}

	/**
	 * Перехват общих сервисных ошибок. Текст сообщения оставляем неизменным.
	 */
	@ExceptionHandler(ServiceException.class)
	@ResponseStatus
	@ResponseBody
	private ClientMessage handleServiceException(ServiceException ex) {
		LOG.error(ex.getMessage(), ex);
		return new ClientMessage(ex.getMessage(), null);
	}

	/**
	 * Перехват ошибок доступа
	 */
	@ExceptionHandler({AccessDeniedException.class, AuthenticationException.class, BadCredentialsException.class})
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	private ClientMessage handleServiceException(AccessDeniedException ex) {
		LOG.error(ex.getMessage(), ex);
		return new ClientMessage(
				ResourceMessages.getMessage(Integer.valueOf(HttpStatus.FORBIDDEN.value()).toString()),
				ExceptionUtils.getStackTrace(ex));
	}

	/**
	 * Перехват ошибок с неправильным запросом
	 */
	@ExceptionHandler({IllegalArgumentException.class, JsonParseException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	private ClientMessage handleServiceException(IllegalArgumentException ex) {
		LOG.error(ex.getMessage(), ex);
		return new ClientMessage(
				ResourceMessages.getMessage(Integer.valueOf(HttpStatus.BAD_REQUEST.value()).toString()),
				ExceptionUtils.getStackTrace(ex));
	}


	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleHttpMessageNotReadable(ex, headers, status, request);
	}
}