package ru.it.sd.hp.problem;

import com.hp.itsm.api.interfaces.IStatusProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.HpApi;
import ru.it.sd.hp.HpCrudDao;
import ru.it.sd.model.EntityStatus;

import java.util.Set;

/**
 * Created by nsyhev 01.08.2018
 */
@Repository
public class IStatusProblemDao implements HpCrudDao<EntityStatus, IStatusProblem> {

    @Autowired
    private HpApi api;


    @Override
    public long create(EntityStatus entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IStatusProblem read(long id) {
        try{
            return api.getSdClient().sd_session().getStatusProblemHome().openStatusProblem(id);
        }catch (Exception e){
            throw new ServiceException("Не найден", e);
        }
    }

    @Override
    public void update(EntityStatus entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}