package ru.it.sd.web.controller.rest;

import ru.it.sd.model.Person;
import ru.it.sd.service.PersonService;
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
public class PersonRestController extends AbstractController{

	@Autowired
	private PersonService personService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Person logout(@PathVariable Long id) {
		return personService.read(id);
	}
}
