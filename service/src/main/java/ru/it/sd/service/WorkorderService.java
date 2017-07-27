package ru.it.sd.service;

import com.hp.itsm.api.ApiSDSession;
import com.hp.itsm.api.interfaces.*;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import ru.it.sd.dao.WorkorderDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.Person;
import ru.it.sd.model.Workorder;
import ru.it.sd.service.external.IWorkorderService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by user on 26.07.2017.
 */
public class WorkorderService implements CrudService<Workorder>{

    @Autowired
    WorkorderDao dao;

    @Autowired
    IWorkorderService iWorkorderService;

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
            long id = iWorkorderService.create(entity);
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
