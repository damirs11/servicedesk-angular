package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.dao.WorkorderDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.IWorkorderDao;
import ru.it.sd.model.GrantRule;
import ru.it.sd.model.Workorder;
import ru.it.sd.service.utils.validation.Validator;
import ru.it.sd.util.ResourceMessages;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by user on 26.07.2017.
 */
@Service
public class WorkorderService extends CrudService<Workorder>{

    private WorkorderDao dao;
    private IWorkorderDao hpDao;
    private SecurityService securityService;
    private AccessService accessService;
    public WorkorderService(WorkorderDao dao, IWorkorderDao hpDao, SecurityService securityService, AccessService accessService) {
        this.dao = dao;
        this.hpDao = hpDao;
        this.securityService = securityService;
        this.accessService = accessService;
    }

    @Override
    public Workorder read(long id) {
        Workorder workorder = dao.read(id);
        if(accessService.getEntityAccess(workorder).getLeft().getRead() != GrantRule.NONE){
            return workorder;
        }else {
            throw new ServiceException(ResourceMessages.getMessage("error.service.access.denied"));
        }
    }

    @Override
    public List<Workorder> list(Map<String, String> filter) {
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
    public Workorder create(Workorder entity) {
            long id = hpDao.create(entity);
            return dao.read(id);
    }

    @Override
    public Workorder update(Workorder entity) {
        Validator.validate(entity);

        try {
            hpDao.update(entity);
            return dao.read(entity.getId());
        } catch (Exception e){
            throw new ServiceException("Возникли проблемы при редактировании наряда. " + e.getMessage(), e);
        }

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Workorder patch(Workorder entity, Set<String> fields) {
        return null;
    }
}
