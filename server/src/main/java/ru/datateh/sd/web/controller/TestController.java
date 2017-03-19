package ru.datateh.sd.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author quadrix
 *         19.03.2017 1:58
 */
@RestController
@EnableWebMvc
public class TestController {

	private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

	@RequestMapping(value = "/test", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public String test() {
		LOG.info("TEST CONTROLLER LOG");
		return "qwerty";
	}
}