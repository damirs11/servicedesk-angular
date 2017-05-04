package com.aplana.sd.service.utils.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Сервис для записи сообщений в журнал запроса
 *
 * @author quadrix
 * @since 20.10.2016
 */
@Service
public class LogService {

	@Autowired
	private ApplicationContext appContext;

	public RequestLog getRequestLog() {
		return (RequestLog) appContext.getBeansOfType(RequestLog.class);
	}

}