package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.dao.WorkorderDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.IWorkorderDao;
import ru.it.sd.model.Workorder;
import ru.it.sd.service.utils.validation.Validator;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by user on 26.07.2017.
 */
@Service
public class WorkorderService implements CrudService<Workorder>{

    private WorkorderDao dao;
    private IWorkorderDao hpDao;
    private SecurityService securityService;

    public WorkorderService(WorkorderDao dao, IWorkorderDao hpDao, SecurityService securityService) {
        this.dao = dao;
        this.hpDao = hpDao;
        this.securityService = securityService;
    }

    @Override
    public Workorder read(long id) {
        return dao.read(id);
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
        Validator.validate(entity);

        try {
            long id = hpDao.create(entity);
            return dao.read(id);
        } catch (Exception e){
            throw new ServiceException("Возникли проблемы при создании наряда. " + e.getMessage(), e);
        }
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
