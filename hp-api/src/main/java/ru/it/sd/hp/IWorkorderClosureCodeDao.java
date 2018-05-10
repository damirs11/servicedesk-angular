package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IChangeClosureCode;
import com.hp.itsm.api.interfaces.IWorkorderClosureCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.EntityClosureCode;

/**
 * Created by nsyhev 29.09.2017
 */
@Repository
public class IWorkorderClosureCodeDao implements HpCrudDao<EntityClosureCode, IWorkorderClosureCode> {

    @Autowired
    private HpApi api;


    @Override
    public long create(EntityClosureCode entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IWorkorderClosureCode read(long id) {
        try{
            return api.getSdClient().sd_session().getWorkorderClosureCodeHome().openWorkorderClosureCode(id);
        }catch (Exception e){
            throw new ServiceException("Не найден ", e);
        }
    }

    @Override
    public void update(EntityClosureCode entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}