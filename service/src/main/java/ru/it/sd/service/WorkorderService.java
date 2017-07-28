package ru.it.sd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.WorkorderDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.IWorkorderDao;
import ru.it.sd.model.Workorder;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by user on 26.07.2017.
 */
//@Service
public class WorkorderService implements CrudService<Workorder>{

    @Autowired
    private WorkorderDao dao;
    @Autowired
    private IWorkorderDao hpDao;

    @Override
    public Workorder read(long id) {
        return dao.read(id);
    }

    @Override
    public List<Workorder> list(Map<String, String> filter) {
        return dao.list(filter);
    }

    @Override
    public int count(Map<String, String> filter) {
        return dao.getTotal(filter);
    }

    @Override
    public Workorder create(Workorder entity) {

        /**
         * Все проверки пришедшей сущности, валидация, проверка прав и.т.д
         */

        try {
            long id = hpDao.create(entity);
            return dao.read(id);
        } catch (Exception e){
            throw new ServiceException("Возникли проблемы при создании наряда");
        }
    }

    @Override
    public Workorder update(Workorder entity) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Workorder patch(Workorder entity, Set<String> fields) {
        return null;
    }
}
