package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IChangeCode1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.EntityCode1;

/**
 * Created by nsyhev 29.09.2017
 */
@Repository
public class IChangeCode1Dao implements HpCrudDao<EntityCode1, IChangeCode1> {

    @Autowired
    private HpApi api;


    @Override
    public long create(EntityCode1 entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IChangeCode1 read(long id) {
        try{
            return api.getSdClient().sd_session().getChangeCode1Home().openChangeCode1(id);
        }catch (Exception e){
            throw new ServiceException("Не найден", e);
        }
    }

    @Override
    public void update(EntityCode1 entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}