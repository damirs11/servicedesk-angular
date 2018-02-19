package ru.it.sd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.TemplateDao;
import ru.it.sd.model.Template;
import ru.it.sd.model.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для работы с согласованием
 * @author nsychev
 * @since 07.10.2017
 */
@Service
public class TemplateService extends CrudService<Template> {

	private final TemplateDao templateDao;
	private final SecurityService securityService;


    @Autowired
    public TemplateService(TemplateDao templateDao, SecurityService securityService) {
        this.templateDao = templateDao;
        this.securityService = securityService;
    }

    @Override
    public Template read(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Template> list(Map<String, String> filter) {
        User user = securityService.getCurrentUser();
        Long accountId = user.getId();
        filter.put("accountId", accountId.toString());
        return templateDao.list(filter);
    }

    @Override
    public int count(Map<String, String> filter) {
        return templateDao.count(filter);
    }

    @Override
    public Template create(Template entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Template update(Template entity) { throw new UnsupportedOperationException();
    }

    @Override
    public Template patch(Template entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}
