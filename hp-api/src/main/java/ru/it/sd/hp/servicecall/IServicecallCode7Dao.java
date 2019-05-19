package ru.it.sd.hp.servicecall;

import com.hp.itsm.api.interfaces.IServicecallCode7;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.HpApi;
import ru.it.sd.hp.HpCrudDao;
import ru.it.sd.model.EntityCode1;

import java.util.Set;

/**
 * Created by nsyhev 29.09.2017
 */
@Repository
public class IServicecallCode7Dao implements HpCrudDao<EntityCode1, IServicecallCode7> {

    @Autowired
    private HpApi api;


    @Override
    public long create(EntityCode1 entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IServicecallCode7 read(long id) {
        try{
            return api.getSdClient().sd_session().getServicecallCode7Home().openServicecallCode7(id);
        }catch (Exception e){
            throw new ServiceException("Не найден", e);
        }
    }

    @Override
    public void update(EntityCode1 entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}