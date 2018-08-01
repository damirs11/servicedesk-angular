package ru.it.sd.hp.change;

import com.hp.itsm.api.interfaces.IStatusChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.HpApi;
import ru.it.sd.hp.HpCrudDao;
import ru.it.sd.model.EntityStatus;

/**
 * Created by nsyhev 29.09.2017
 */
@Repository
public class IStatusChangeDao implements HpCrudDao<EntityStatus, IStatusChange> {

    @Autowired
    private HpApi api;


    @Override
    public long create(EntityStatus entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IStatusChange read(long id) {
        try{
            return api.getSdClient().sd_session().getStatusChangeHome().openStatusChange(id);
        }catch (Exception e){
            throw new ServiceException("Не найден", e);
        }
    }

    @Override
    public void update(EntityStatus entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}