package com.aplana.sd.web;

import com.aplana.sd.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.aplana.sd.model.User;
import com.aplana.sd.util.ResourceMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.aplana.sd.util.ResourceMessages.*;

/**
 * Устанавливает значения переменных для логов. Логирует параметры вызова и статус результата.
 * Перехватываются только обращения к контроллерам, при запросе статического контента методы
 * данного класса не вызываются.
 *
 * @author quadrix
 * @since 24.10.2016
 */
@Service
public class AppLoggingInterceptor extends HandlerInterceptorAdapter {

	private final Logger LOG = LoggerFactory.getLogger(AppLoggingInterceptor.class);

	private static final String MDC_USER 	= "app_user";
	private static final String MDC_QUERY 	= "app_query";
	private static final String MDC_HOST 	= "app_host";

	@Autowired
	private SecurityService securityService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		setLogParams(request);
		LOG.debug(getMessage("http.request.start"));
		return true;
	}

	/**
	 * Устанавливает значения переменных для вывода в лог
	 */
	private void setLogParams(HttpServletRequest request) {
		User user = securityService.getCurrentUser();
		MDC.put(MDC_USER, user == null ? ResourceMessages.getMessage("default.login") : user.toString());
		// Адресная строка. Пример: GET:/ds/rest/entity/Tariff&fulltext=&paging=1;100&sort=name-asc
		MDC.put(MDC_QUERY, request.getMethod() + ':' + request.getRequestURI() + (request.getQueryString() == null ? "" : '?' + request.getQueryString()));
		// "X-FORWARDED-FOR" - актуально для переадресации от балансировщика или прокси-сервера
		MDC.put(MDC_HOST,
				request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") :
						request.getRemoteHost() != null ? request.getRemoteHost() : request.getRemoteAddr());
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		LogComplete(response);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		LogComplete(response);
	}

	/**
	 * Вызывает по окончанию обработки запроса пользователя
	 * @param response выводит в лог информацию о статусе ответа на запрос клиента
	 */
	private void LogComplete(HttpServletResponse response) {
		LOG.debug(getMessage("http.request.result", response.getStatus(), HttpStatus.valueOf(response.getStatus()).name()));
	}
}