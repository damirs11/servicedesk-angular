package com.aplana.sd.web.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Тестовый контроллер.
 * todo удалить класс перед релизом
 *
 * @author quadrix
 * @since 19.03.2017
 */
@RestController
public class RestTestController {

	private static final Logger LOG = LoggerFactory.getLogger(RestTestController.class);

	@RequestMapping(value = "/rest/test", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public String test() {
		LOG.info("TEST CONTROLLER LOG");
		return "qwerty";
	}
}