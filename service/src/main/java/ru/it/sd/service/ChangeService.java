package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ChangeDao;
import ru.it.sd.model.Change;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для работы с изменениями
 * @author quadrix
 * @since 13.05.2017
 */
@Service
public class ChangeService implements CrudService<Change>{

	private static final Logger logger = LoggerFactory.getLogger(ChangeService.class);

	@Autowired
	private ChangeDao dao;

	@Override
	public Change read(long id) {
		return dao.read(id);
	}

	@Override
	public List<Change> list(Map<String, String> filter) {
		return dao.list(filter);
	}

	@Override
	public int count(Map<String, String> filter) {
		return dao.count(filter);
	}

	@Override
	public Change create(Change entity) {
		//todo
		entity.setId(Math.round(Math.random() * 100000));
		return entity;
	}

	@Override
	public Change update(Change entity) {
		//todo
		return entity;
	}

	@Override
	public void delete(long id) {
		//todo
	}

	@Override
	public Change patch(Change entity, Set<String> fields) {
		//todo
		return entity;
	}

}
