package ru.it.sd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ServiceLevelAgreementDao;
import ru.it.sd.model.ServiceLevelAgreement;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для SLA
 * @author nsychev
 */
@Service
public class SLAService extends CrudService<ServiceLevelAgreement> {

    private final ServiceLevelAgreementDao serviceLevelAgreementDao;

    @Autowired
    public SLAService(ServiceLevelAgreementDao serviceLevelAgreementDao) {
        this.serviceLevelAgreementDao = serviceLevelAgreementDao;
    }

    @Override
    public ServiceLevelAgreement read(long id) {
        return serviceLevelAgreementDao.read(id);
    }

    @Override
    public List<ServiceLevelAgreement> list(Map<String, String> filter) {
        return serviceLevelAgreementDao.list(filter);
    }

    @Override
    public int count(Map<String, String> filter) {
        return serviceLevelAgreementDao.count(filter);
    }

    @Override
    public ServiceLevelAgreement create(ServiceLevelAgreement entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServiceLevelAgreement update(ServiceLevelAgreement entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServiceLevelAgreement patch(ServiceLevelAgreement entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

}
