package ru.it.sd.hp.servicecall;

import com.hp.itsm.api.interfaces.IMedium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.HpApi;
import ru.it.sd.hp.HpCrudDao;
import ru.it.sd.model.Source;

import java.util.Set;

/**
 * Created by nsyhev 29.09.2017
 */
@Repository
public class IServicecallMediumDao implements HpCrudDao<Source, IMedium> {

    @Autowired
    private HpApi api;


    @Override
    public long create(Source entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IMedium read(long id) {
        try{
            return api.getSdClient().sd_session().getMediumHome().openMedium(id);
        }catch (Exception e){
            throw new ServiceException("Не найден", e);
        }
    }

    @Override
    public void update(Source entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}