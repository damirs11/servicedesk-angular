package ru.it.sd.hp.problem;

import com.hp.itsm.api.interfaces.IProblemClosureCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.HpApi;
import ru.it.sd.hp.HpCrudDao;
import ru.it.sd.model.EntityClosureCode;

/**
 * Created by nsyhev 29.09.2017
 */
@Repository
public class IProblemClosureCodeDao implements HpCrudDao<EntityClosureCode, IProblemClosureCode> {

    @Autowired
    private HpApi api;


    @Override
    public long create(EntityClosureCode entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IProblemClosureCode read(long id) {
        try{
            return api.getSdClient().sd_session().getProblemClosureCodeHome().openProblemClosureCode(id);
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