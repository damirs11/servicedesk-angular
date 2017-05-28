package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ChangeDao;
import ru.it.sd.model.Change;

import java.util.List;
import java.util.Map;

/**
 * Сервис для работы с изменениями
 * @author quadrix
 * @since 13.05.2017
 */
@Service
public class ChangeService implements ReadService<Change> {

	private static final Logger LOG = LoggerFactory.getLogger(ChangeService.class);

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
}
