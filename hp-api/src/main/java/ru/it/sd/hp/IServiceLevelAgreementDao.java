package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IServiceLevelAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.ServiceLevelAgreement;

import java.util.Set;

/**
 * Created by user on 27.07.2017.
 */
@Repository
public class IServiceLevelAgreementDao implements HpCrudDao<ServiceLevelAgreement, IServiceLevelAgreement> {

    @Autowired
    private HpApi api;

    @Override
    public long create(ServiceLevelAgreement entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IServiceLevelAgreement read(long id) {
        try {
            return api.getSdClient().sd_session().getServiceLevelAgreementHome().openServiceLevelAgreement(id);
        } catch (Exception e){
            throw new ServiceException("Сущность не найдена "+e.getMessage(),e);
        }
    }

    @Override
    public void update(ServiceLevelAgreement entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}