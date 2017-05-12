package com.aplana.sd.web.controller.rest;

import com.aplana.sd.model.Person;
import com.aplana.sd.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с информацияей о людях
 *
 * @author quadrix
 * @since 13.05.2017
 */
@RestController
@RequestMapping(value = "/rest/service/person", produces = "application/json;charset=UTF-8")
public class RestPersonController extends AbstractController{

	@Autowired
	private PersonService personService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Person logout(@PathVariable Long id) {
		return personService.get(id);
	}
}
