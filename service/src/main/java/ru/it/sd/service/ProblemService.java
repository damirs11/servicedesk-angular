package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ProblemDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.GrantRule;
import ru.it.sd.model.Problem;
import ru.it.sd.service.utils.validation.Validator;
import ru.it.sd.util.ResourceMessages;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для работы с "Проблемами"
 * @author quadrix
 * @since 28.07.2018
 */
@Service
public class ProblemService extends CrudService<Problem>{

	private static final Logger logger = LoggerFactory.getLogger(ProblemService.class);

	private ProblemDao dao;
	private SecurityService securityService;
	private AccessService accessService;
	public ProblemService(ProblemDao dao, SecurityService securityService, AccessService accessService) {
		this.dao = dao;
		this.securityService = securityService;
		this.accessService = accessService;
	}

	@Override
	public Problem read(long id) {
		Problem change = dao.read(id);
		if(accessService.getEntityAccess(change).getLeft().getRead() != GrantRule.NONE){
			return change;
		}else {
			throw new ServiceException(ResourceMessages.getMessage("error.service.access.denied"));
		}
	}

	@Override
	public List<Problem> list(Map<String, String> filter) {
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
	public Problem create(Problem entity) {
        try {
            //todo
	        throw new UnsupportedOperationException();
        } catch (Exception e){
            throw new ServiceException("Возникли проблемы при создании проблемы. " + e.getMessage(), e);
        }
	}

	@Override
	public Problem update(Problem entity) {
		Validator.validate(entity);
		try{
			//todo
			throw new UnsupportedOperationException();
        }catch (Exception e){
            throw new ServiceException("Возникли проблемы при редактировании проблемы. " + e.getMessage(), e);
        }

	}

	@Override
	public void delete(long id) {
		//todo
	}

	@Override
	public Problem patch(Problem entity, Set<String> fields) {
		//todo
		throw new UnsupportedOperationException();
	}

}
