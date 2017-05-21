package ru.it.sd.service;

import ru.it.sd.dao.PersonDao;
import ru.it.sd.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Сервис для работы с информацией о людях
 * @author quadrix
 * @since 13.05.2017
 */
@Service
public class PersonService implements ReadService<Person> {

	private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

	@Autowired
	private PersonDao dao;

	@Override
	public Person read(long id) {
		return dao.read(id);
	}

	@Override
	public List<Person> list(Map<String, String> filter) {
		return dao.list(filter);
	}
}
