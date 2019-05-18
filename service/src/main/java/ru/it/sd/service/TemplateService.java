package ru.it.sd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.TemplateDao;
import ru.it.sd.model.Role;
import ru.it.sd.model.Template;
import ru.it.sd.model.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для работы с шаблонами
 *
 * @author nsychev
 * @since 09.02.2018
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
        return templateDao.read(id);
    }

    @Override
    public List<Template> list(Map<String, String> filter) {
        User user = securityService.getCurrentUser();
        List<Role> roles = user.getRoles();
        boolean first = true;
        StringBuilder rolesString = new StringBuilder();
        for (Role role : roles) {
            if (!first) {
                rolesString.append(",");
            }
            rolesString.append(role.getId());
            first = false;
        }
        filter.put("roles", rolesString.toString());
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
    public Template update(Template entity) {
        throw new UnsupportedOperationException();
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
