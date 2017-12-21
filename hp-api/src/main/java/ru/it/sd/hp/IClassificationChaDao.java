package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IClassificationCha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.EntityClassification;

/**
 * Created by nsyhev 29.09.2017
 */
@Repository
public class IClassificationChaDao implements HpCrudDao<EntityClassification, IClassificationCha> {

    @Autowired
    private HpApi api;

    @Override
    public long create(EntityClassification entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IClassificationCha read(long id) {
        try{
            return api.getSdClient().sd_session().getClassificationChaHome().openClassificationCha(id);
        }catch (Exception e){
            throw new ServiceException("Не найдена категория изменения. "+e.getMessage(),e);
        }

    }

    @Override
    public void update(EntityClassification entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}