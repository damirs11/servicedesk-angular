package ru.it.sd.hp.change;

import com.hp.itsm.api.interfaces.IChangeClosureCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.HpApi;
import ru.it.sd.hp.HpCrudDao;
import ru.it.sd.model.EntityClosureCode;

import java.util.Set;

/**
 * Created by nsyhev 29.09.2017
 */
@Repository
public class IChangeClosureCodeDao implements HpCrudDao<EntityClosureCode, IChangeClosureCode> {

    @Autowired
    private HpApi api;


    @Override
    public long create(EntityClosureCode entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IChangeClosureCode read(long id) {
        try{
            return api.getSdClient().sd_session().getChangeClosureCodeHome().openChangeClosureCode(id);
        }catch (Exception e){
            throw new ServiceException("Не найден ", e);
        }
    }

    @Override
    public void update(EntityClosureCode entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}