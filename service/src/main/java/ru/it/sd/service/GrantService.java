package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.GrantDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.Grant;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для работы с изменениями
 * @author quadrix
 * @since 13.05.2017
 */
@Service
public class GrantService extends CrudService<Grant>{

	private static final Logger logger = LoggerFactory.getLogger(GrantService.class);

	private GrantDao dao;

	public GrantService(GrantDao dao) {
		this.dao = dao;
	}

	@Override
	public Grant read(long id) {
		return dao.read(id);
	}

	@Override
	public List<Grant> list(Map<String, String> filter) {

		return dao.list(filter);
	}

	@Override
	public int count(Map<String, String> filter) {
		return dao.count(filter);
	}

	@Override
	public Grant create(Grant entity) {
            throw new ServiceException("Возникли проблемы при создании изменения. " );

	}

	@Override
	public Grant update(Grant entity) {

			throw new ServiceException("Возникли проблемы при редактировании изменения. " );


	}

	@Override
	public void delete(long id) {
		//todo
	}

	@Override
	public Grant patch(Grant entity, Set<String> fields) {
		return null;
	}

}
