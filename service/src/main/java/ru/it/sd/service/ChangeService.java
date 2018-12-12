package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ChangeDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.change.IChangeDao;
import ru.it.sd.model.Change;
import ru.it.sd.model.GrantRule;
import ru.it.sd.util.ResourceMessages;

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
	private AccessService accessService;
	public ChangeService(ChangeDao dao, SecurityService securityService, IChangeDao iChangeDao, AccessService accessService) {
		this.dao = dao;
		this.securityService = securityService;
		this.iChangeDao = iChangeDao;
		this.accessService = accessService;
	}

	@Override
	public Change read(long id) {
		Change change = dao.read(id);
		if (change == null) return null;
		if(accessService.getEntityAccess(change).getLeft().getRead() != GrantRule.NONE){
			return change;
		}else {
			throw new SecurityException(ResourceMessages.getMessage("error.service.access.denied"));
		}
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
        try {
            long id = iChangeDao.create(entity);
            return dao.read(id);
        } catch (Exception e){
            throw new ServiceException("Возникли проблемы при создании изменения. " + e.getMessage(), e);
        }
	}

	@Override
	public Change update(Change entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Change patch(Change entity, Set<String> fields) {
		iChangeDao.update(entity, fields);
		return dao.read(entity.getId());
	}
}
