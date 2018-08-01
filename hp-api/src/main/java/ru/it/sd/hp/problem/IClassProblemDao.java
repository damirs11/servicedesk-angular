package ru.it.sd.hp.problem;

import com.hp.itsm.api.interfaces.IClassProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.HpApi;
import ru.it.sd.hp.HpCrudDao;
import ru.it.sd.model.EntityClassification;

/**
 * Created by nsyhev 29.09.2017
 */
@Repository
public class IClassProblemDao implements HpCrudDao<EntityClassification, IClassProblem> {

    @Autowired
    private HpApi api;

    @Override
    public long create(EntityClassification entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IClassProblem read(long id) {
        try{
            return api.getSdClient().sd_session().getClassProblemHome().openClassProblem(id);
        }catch (Exception e){
            throw new ServiceException("Не найдена классификация изменения. " + e.getMessage(), e);
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