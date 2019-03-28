package ru.it.sd.hp.servicecall;

import com.hp.itsm.api.interfaces.IClassificationSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.HpApi;
import ru.it.sd.hp.HpCrudDao;
import ru.it.sd.model.EntityClassification;

import java.util.Set;

/**
 * Created by nsyhev 29.09.2017
 */
@Repository
public class IClassificationServicecallDao implements HpCrudDao<EntityClassification, IClassificationSer> {

    @Autowired
    private HpApi api;


    @Override
    public long create(EntityClassification entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IClassificationSer read(long id) {
        try{
            return api.getSdClient().sd_session().getClassificationSerHome().openClassificationSer(id);
        }catch (Exception e){
            throw new ServiceException("Не найден", e);
        }
    }

    @Override
    public void update(EntityClassification entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}