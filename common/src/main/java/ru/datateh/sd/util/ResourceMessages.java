package ru.datateh.sd.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Класс для работы с текстовыми константами
 *
 * @author quadrix
 * 02.12.2016 17:11
 */
public class ResourceMessages {

	private static final ResourceBundle resource = ResourceBundle.getBundle("messages", new Locale(""));

	public static String getMessage(String key, Object args)  {
		String message = resource.getString(key);
		return args == null ? message : MessageFormat.format(message, args);
	}

	public static String getMessage(String key)  {
		return getMessage(key, null);
	}

	public static ResourceBundle getResourceBundle() {
		return resource;
	}
}