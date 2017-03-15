package ru.datateh.sd.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Выводит информацию в лог о сессии пользователя
 *
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a>
 * @since 03.11.2016 18:30
 */
@WebListener
public class AppSessionListener implements HttpSessionListener {

	private static final Logger LOG = LoggerFactory.getLogger(AppSessionListener.class);

	private static final long MS_IN_SEC = 1000;
	private static final long MS_IN_MIN = MS_IN_SEC * 60;
	private static final long MS_IN_HOUR = MS_IN_MIN * 60;

	private static final String SPRING_ATTRIBUTE_NAME = "SPRING_SECURITY_CONTEXT";
	public static final String NEW_SESSION_FLAG_NAME = "NEW_SESSION";

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		LOG.debug("Session (" + se.getSession().getId() + ") is created. Inactive timeout = " + se.getSession().getMaxInactiveInterval() + " seconds" +
				getAuthInfo(se.getSession().getAttribute(SPRING_ATTRIBUTE_NAME)));
		se.getSession().setAttribute(NEW_SESSION_FLAG_NAME, NEW_SESSION_FLAG_NAME);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		LOG.debug("Session (" + se.getSession().getId() + ") is destroyed. Session age is " +
				getIntervalString(new Date().getTime() - se.getSession().getCreationTime()) +
				getAuthInfo(se.getSession().getAttribute(SPRING_ATTRIBUTE_NAME)));
	}

	/**
	 * Возвращает информацию о текущем пользователе из веб-сессии
	 * @param springInfo информация о контексте Spring
	 * @return строка"
	 */
	static String getAuthInfo(Object springInfo) {
		Pattern p = Pattern.compile("\\[.*\\]");
		Matcher m = p.matcher(springInfo == null ? "" : springInfo.toString());
		if (m.find()) {
			return " - " + m.group();
		}
		return "";
	}

	/**
	 * По числе миллисекунд возвращает название интервала в часах, минутах и т.д. до мс.
	 *
	 * @param interval интервал числом
	 * @return интервал в виде текста
	 */
	static String getIntervalString(long interval) {
		StringBuilder sb = new StringBuilder();
		long hours = interval / MS_IN_HOUR;
		interval -= hours * MS_IN_HOUR;
		long minutes = interval / MS_IN_MIN;
		interval -= minutes * MS_IN_MIN;
		long seconds = interval / MS_IN_SEC;
		interval -= seconds * MS_IN_SEC;
		long ms = interval;

		if (hours > 0) {
			sb.append(hours);
			sb.append(" hours ");
		}
		if (minutes > 0) {
			sb.append(minutes);
			sb.append(" minutes ");
		}
		if (seconds > 0) {
			sb.append(seconds);
			sb.append(" seconds ");
		}
		if (ms > 0) {
			sb.append(ms);
			sb.append(" ms ");
		}
		return sb.toString();
	}
}