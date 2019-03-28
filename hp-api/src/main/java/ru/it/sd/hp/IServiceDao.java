package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.Service;

import java.util.Set;

/**
 * Created by user on 27.07.2017.
 */
@Repository
public class IServiceDao implements HpCrudDao<Service, IService> {

    @Autowired
    private HpApi api;

    @Override
    public long create(Service entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IService read(long id) {
        try {
            return api.getSdClient().sd_session().getServiceHome().openService(id);
        } catch (Exception e){
            throw new ServiceException("Сущность не найдена "+e.getMessage(),e);
        }
    }

    @Override
    public void update(Service entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}