package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ServiceCallDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.servicecall.IServicecallDao;
import ru.it.sd.model.ServiceCall;
import ru.it.sd.model.Template;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для работы с изменениями
 * @author quadrix
 * @since 13.05.2017
 */
@Service
public class ServiceCallService extends CrudService<ServiceCall> implements HasTemplateService<ServiceCall>{

	private static final Logger logger = LoggerFactory.getLogger(ServiceCallService.class);

	private final ServiceCallDao dao;
	private final SecurityService securityService;
	private final AccessService accessService;
	private final IServicecallDao iServicecallDao;
	@Autowired
	public ServiceCallService(ServiceCallDao dao, SecurityService securityService, AccessService accessService, IServicecallDao iServicecallDao) {
		this.dao = dao;
		this.securityService = securityService;
		this.accessService = accessService;
		this.iServicecallDao = iServicecallDao;
	}

	@Override
	public ServiceCall read(long id) {
		ServiceCall serviceCall = dao.read(id);
		return serviceCall;
		/*if (serviceCall == null) return null;
		if(accessService.getEntityAccess(serviceCall).getLeft().getRead() != GrantRule.NONE){
			return serviceCall;
		}else {
			throw new SecurityException(ResourceMessages.getMessage("error.service.access.denied"));
		}*/
	}

	@Override
	public List<ServiceCall> list(Map<String, String> filter) {
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
	public ServiceCall create(ServiceCall entity) {
        try {
            long id = iServicecallDao.create(entity);
            return dao.read(id);
        } catch (Exception e){
            throw new ServiceException("Возникли проблемы при создании заявки. " + e.getMessage(), e);
        }
	}

	@Override
	public ServiceCall update(ServiceCall entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ServiceCall patch(ServiceCall entity, Set<String> fields) {
		iServicecallDao.update(entity, fields);
		return dao.read(entity.getId());
	}

	@Override
	public ServiceCall getTemplate(Template template) {
		/*Map<String, String> filter = new HashMap<>();
		filter.put("id", template.getId().toString());
		filter.put("entityId", template.getEntityType().getId().toString());
		List<Template> templates = templateDao.list(filter);
		if (!templates.isEmpty()) {
			return dao.getTemplate(template);
		}
		throw new ServiceException("Шаблон не найден");*/
		return null;
	}
}
