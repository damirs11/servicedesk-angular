package com.aplana.sd.exception;

/**
 * Класс-обертка для сообщений со стеком строк. Может использоваться для обертки исключений при передачи клиенту.
 *
 * @author quadrix
 * @since 24.06.2016
 */

public class ClientMessage {

	/**
	 * Краткое описание сообщения. Заголовок.
	 */
	private String text;
	/**
	 * Более детальное описание сообщения. Может использоваться для вывода стек-трейса исключения.
	 */
	private String details;

	public ClientMessage(String text, String details) {
		this.text = text;
		this.details = details;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * Если статус ответа отличен от 200 и размер ответа меньше 512 байт - ie8 покажет свою страницу ошибки, а наш
	 * ответ проигнорирует. Чтобы этого избежать - добавим мусор в ответ.
	 * http://stackoverflow.com/questions/11544048/how-do-i-suppress-friendly-error-messages-in-internet-explorer
	 */
	public String ieFriendlyErrorFix = IE_FRIENDLY_ERROR_FIX;
	private final static String IE_FRIENDLY_ERROR_FIX = new String(new char[512]).replace("\0", " ");
}