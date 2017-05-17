package ru.it.sd.service;

import ru.it.sd.dao.PersonDao;
import ru.it.sd.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с информацией о людях
 * @author quadrix
 * @since 13.05.2017
 */
@Service
public class PersonService {

	private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

	@Autowired
	private PersonDao dao;

	/**
	 * Получает информацию о человеке по его идентификатору
	 * @param id идентификатор человека
	 * @return информация о человеке
	 */
	public Person get(Long id) {
		return dao.findOne(id);
	}

}
