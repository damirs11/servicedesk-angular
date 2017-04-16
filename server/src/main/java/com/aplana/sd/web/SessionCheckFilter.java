package com.aplana.sd.web;

import com.aplana.sd.util.ResourceMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Фильтр для проверки состояния сессии. Если клиентский запрос пришел с неактуальным
 * id сессии, то возвращаем ответ "401 - не авторизован" несмотря на то, что была
 * создана новая сессия.</p>
 *
 * <p>Это было сделано для того, чтобы клиент актуализировал данные на своей стороне</p>
 *
 * quadrix 07.11.2016 17:27
 */
public class SessionCheckFilter implements Filter {

	private final Logger LOG = LoggerFactory.getLogger(SessionCheckFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;

			if (AppSessionListener.NEW_SESSION_FLAG_NAME.equals(req.getSession().getAttribute(AppSessionListener.NEW_SESSION_FLAG_NAME))) {
				req.getSession().removeAttribute(AppSessionListener.NEW_SESSION_FLAG_NAME);
				String contextPath = req.getServletContext().getContextPath();
				if (req.isRequestedSessionIdFromCookie() && !req.isRequestedSessionIdValid() &&
						req.getRequestURI().startsWith(contextPath + "/rest")) { // перехватываем только ajaх-запросы
					LOG.debug(ResourceMessages.getMessage("http.session.refresh.client", req.getRequestURI()));
					res.reset();
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}