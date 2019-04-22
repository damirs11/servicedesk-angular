package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.TemplateDao;
import ru.it.sd.dao.WorkorderDao;
import ru.it.sd.dao.utils.FilterMap;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.workorder.IWorkorderDao;
import ru.it.sd.model.GrantRule;
import ru.it.sd.model.Template;
import ru.it.sd.model.Workorder;
import ru.it.sd.util.ResourceMessages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by user on 26.07.2017.
 */
@Service
public class WorkorderService extends CrudService<Workorder> implements HasTemplateService<Workorder> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkorderService.class);

    private WorkorderDao dao;
    private IWorkorderDao hpDao;
    private SecurityService securityService;
    private AccessService accessService;
    private TemplateDao templateDao;
    public WorkorderService(WorkorderDao dao, IWorkorderDao hpDao, SecurityService securityService, AccessService accessService, TemplateDao templateDao) {
        this.dao = dao;
        this.hpDao = hpDao;
        this.securityService = securityService;
        this.accessService = accessService;
        this.templateDao = templateDao;
    }

    @Override
    public Workorder read(long id) {
        Workorder workorder = dao.read(id);
        if (workorder == null) return null;
        if (accessService.getEntityAccess(workorder).getLeft().getRead() != GrantRule.NONE) {
            return workorder;
        } else {
            throw new SecurityException(ResourceMessages.getMessage("error.service.access.denied"));
        }
    }

    @Override
    public List<Workorder> list(Map<String, String> filter) {
        securityService.addCurrentUserToFilter(filter);
        FilterMap filterMap = new FilterMap();
        filterMap.putAll(filter);
        accessService.applyReadFilter(filterMap, Workorder.class);
        List<Workorder> list = dao.list(filterMap, AbstractEntityDao.MapperMode.LIST);
        return list;
    }

    @Override
    public int count(Map<String, String> filter) {
        securityService.addCurrentUserToFilter(filter);
        FilterMap filterMap = new FilterMap();
        filterMap.putAll(filter);
        accessService.applyReadFilter(filterMap, Workorder.class);
        return dao.count(filterMap);
    }

    @Override
    public Workorder create(Workorder entity) {
        long id = hpDao.create(entity);
        return dao.read(id);
    }

    @Override
    public Workorder update(Workorder entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Workorder patch(Workorder entity, Set<String> fields) {
        try {
            hpDao.update(entity, fields);
            return dao.read(entity.getId());
        } catch (Exception e) {
            throw new ServiceException("Возникли проблемы при редактировании наряда. " + e.getMessage(), e);
        }
    }

    @Override
    public Workorder getTemplate(Template template) {
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
