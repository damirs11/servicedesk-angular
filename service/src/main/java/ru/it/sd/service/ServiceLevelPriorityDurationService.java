package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.ServiceLevelPriorityDurationDao;
import ru.it.sd.model.ServiceLevelPriorityDuration;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для условий предоставления SLA
 *
 * @author nsychev
 */
@Service
public class ServiceLevelPriorityDurationService extends CrudService<ServiceLevelPriorityDuration> {

    private final ServiceLevelPriorityDurationDao dao;

    public ServiceLevelPriorityDurationService(ServiceLevelPriorityDurationDao dao) {
        this.dao = dao;
    }

    @Override
    public ServiceLevelPriorityDuration read(long id) {
        return dao.read(id);
    }

    @Override
    public List<ServiceLevelPriorityDuration> list(Map<String, String> filter) {
        return dao.list(filter, AbstractEntityDao.MapperMode.LIST);
    }

    @Override
    public int count(Map<String, String> filter) {
        return dao.count(filter);
    }

    @Override
    public ServiceLevelPriorityDuration create(ServiceLevelPriorityDuration entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServiceLevelPriorityDuration update(ServiceLevelPriorityDuration entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServiceLevelPriorityDuration patch(ServiceLevelPriorityDuration entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

}
