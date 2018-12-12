package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ProblemDao;
import ru.it.sd.dao.TemplateDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.problem.IProblemDao;
import ru.it.sd.model.GrantRule;
import ru.it.sd.model.Problem;
import ru.it.sd.model.Template;
import ru.it.sd.util.ResourceMessages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для работы с "Проблемами"
 * @author quadrix
 * @since 28.07.2018
 */
@Service
public class ProblemService extends CrudService<Problem> implements HasTemplateService<Problem>{

	private static final Logger logger = LoggerFactory.getLogger(ProblemService.class);

	private ProblemDao dao;
	private SecurityService securityService;
	private AccessService accessService;
	private IProblemDao iProblemDao;
	private TemplateDao templateDao;
	public ProblemService(ProblemDao dao, IProblemDao iProblemDao, SecurityService securityService, AccessService accessService, TemplateDao templateDao) {
		this.dao = dao;
		this.securityService = securityService;
		this.accessService = accessService;
		this.iProblemDao = iProblemDao;
		this.templateDao = templateDao;
	}

	@Override
	public Problem read(long id) {
		Problem problem = dao.read(id);
		if (problem == null) return null;
		if(accessService.getEntityAccess(problem).getLeft().getRead() != GrantRule.NONE){
			return problem;
		}else {
			throw new SecurityException(ResourceMessages.getMessage("error.service.access.denied"));
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
			long id = iProblemDao.create(entity);
            return dao.read(id);
        } catch (Exception e){
            throw new ServiceException("Возникли проблемы при создании проблемы. " + e.getMessage(), e);
        }
	}

	@Override
	public Problem update(Problem entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(long id) {
		iProblemDao.delete(id);
	}

	@Override
	public Problem patch(Problem entity, Set<String> fields) {
		try{
			//todo подумать о необходимости Set<String> fields
			iProblemDao.update(entity, fields);
			return dao.read(entity.getId());
		}catch (Exception e){
			throw new ServiceException("Возникли проблемы при редактировании проблемы. " + e.getMessage(), e);
		}
	}
	@Override
	public Problem getTemplate(Template template) {
		Map<String, String> filter = new HashMap<>();
		filter.put("id", template.getId().toString());
		filter.put("entityId", template.getEntityType().getId().toString());
		List<Template> templates = templateDao.list(filter);
		if (!templates.isEmpty()) {
			return dao.getTemplate(template);
		}
		throw new ServiceException("Шаблон не найден");
	}

}
