package ru.it.sd.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.it.sd.dao.ServiceDao;
import ru.it.sd.model.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис\услуга
 */
@org.springframework.stereotype.Service
public class ServiceService extends CrudService<Service> {

    private final ServiceDao serviceDao;

    @Autowired
    public ServiceService(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    @Override
    public Service read(long id) {
        return serviceDao.read(id);
    }

    @Override
    public List<Service> list(Map<String, String> filter) {
        return serviceDao.list(filter);
    }

    @Override
    public int count(Map<String, String> filter) {
        return serviceDao.count(filter);
    }

    @Override
    public Service create(Service entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Service update(Service entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Service patch(Service entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

}
