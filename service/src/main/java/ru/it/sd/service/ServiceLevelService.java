package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.ServiceLevelDao;
import ru.it.sd.model.ServiceLevel;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для условий предоставления SLA
 *
 * @author nsychev
 */
@Service
public class ServiceLevelService extends CrudService<ServiceLevel> {

    private final ServiceLevelDao serviceLevelDao;

    public ServiceLevelService(ServiceLevelDao serviceLevelDao) {
        this.serviceLevelDao = serviceLevelDao;
    }

    @Override
    public ServiceLevel read(long id) {
        return serviceLevelDao.read(id);
    }

    @Override
    public List<ServiceLevel> list(Map<String, String> filter) {
        return serviceLevelDao.list(filter, AbstractEntityDao.MapperMode.LIST);
    }

    @Override
    public int count(Map<String, String> filter) {
        return serviceLevelDao.count(filter);
    }

    @Override
    public ServiceLevel create(ServiceLevel entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServiceLevel update(ServiceLevel entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServiceLevel patch(ServiceLevel entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

}
