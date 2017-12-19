package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ChangeDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.IChangeDao;
import ru.it.sd.model.Change;
import ru.it.sd.service.utils.validation.Validator;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для работы с изменениями
 * @author quadrix
 * @since 13.05.2017
 */
@Service
public class ChangeService extends CrudService<Change>{

	private static final Logger logger = LoggerFactory.getLogger(ChangeService.class);

	private ChangeDao dao;
	private SecurityService securityService;
	private IChangeDao iChangeDao;

	public ChangeService(ChangeDao dao, SecurityService securityService, IChangeDao iChangeDao) {
		this.dao = dao;
		this.securityService = securityService;
		this.iChangeDao = iChangeDao;
	}

	@Override
	public Change read(long id) {
		return dao.read(id);
	}

	@Override
	public List<Change> list(Map<String, String> filter) {
		// todo проверить, что в фильтре не указаны "левые" группы, к которым пользователь не имеет доступа
		securityService.addCurrentUserToFilter(filter);
		return dao.list(filter);
	}

	@Override
	public int count(Map<String, String> filter) {
		// todo проверить, что в фильтре не указаны "левые" группы, к которым пользователь не имеет доступа
		securityService.addCurrentUserToFilter(filter);
		return dao.count(filter);
	}

	@Override
	public Change create(Change entity) {
        Validator.validate(entity);
        try {
            long id = iChangeDao.create(entity);
            return dao.read(id);
        } catch (Exception e){
            throw new ServiceException("Возникли проблемы при создании изменения. " + e.getMessage(), e);
        }
	}

	@Override
	public Change update(Change entity) {
		Validator.validate(entity);
		try{
		    iChangeDao.update(entity);
            return dao.read(entity.getId());
        }catch (Exception e){
            throw new ServiceException("Возникли проблемы при редактировании изменения. " + e.getMessage(), e);
        }

	}

	@Override
	public void delete(long id) {
		//todo
	}

	@Override
	public Change patch(Change entity, Set<String> fields) {
		iChangeDao.patch(entity, fields);
		return entity;
	}

}
