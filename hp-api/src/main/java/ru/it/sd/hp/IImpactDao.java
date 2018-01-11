package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IImpact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.EntityPriority;

/**
 * Created by nsyhev 29.09.2017
 */
@Repository
public class IImpactDao implements HpCrudDao<EntityPriority, IImpact> {

    @Autowired
    private HpApi api;

    @Override
    public long create(EntityPriority entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IImpact read(long id) {
        try{
            return api.getSdClient().sd_session().getImpactHome().openImpact(id);
        }catch (Exception e){
            throw new ServiceException("Не найден приоритет. "+e.getMessage(),e);
        }

    }

    @Override
    public void update(EntityPriority entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}