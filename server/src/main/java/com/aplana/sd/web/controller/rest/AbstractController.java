package com.aplana.sd.web.controller.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Базовый класс для контроллеров. Содержит в себе общие поля и методы контроллеров.
 *
 * @author quadrix
 * @since 13.05.2017
 */
public abstract class AbstractController {

	/** Настройка маппинга json в объектные типы */
	protected static final ObjectMapper objectMapper = new ObjectMapper()
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
}
