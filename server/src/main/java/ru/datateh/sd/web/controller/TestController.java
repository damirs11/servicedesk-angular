package ru.datateh.sd.web.controller;

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

	@RequestMapping(value = "/test", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public String test() {
		return "qwerty";
	}
}