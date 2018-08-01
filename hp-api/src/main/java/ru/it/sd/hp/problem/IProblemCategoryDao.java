package ru.it.sd.hp.problem;

import com.hp.itsm.api.interfaces.IProblemCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.HpApi;
import ru.it.sd.hp.HpCrudDao;
import ru.it.sd.model.EntityCategory;

/**
 * Created by nsyhev 29.09.2017
 */
@Repository
public class IProblemCategoryDao implements HpCrudDao<EntityCategory, IProblemCategory> {

    @Autowired
    private HpApi api;

    @Override
    public long create(EntityCategory entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IProblemCategory read(long id) {
        try{
            return api.getSdClient().sd_session().getProblemCategoryHome().openProblemCategory(id);
        }catch (Exception e){
            throw new ServiceException("Не найдена категория изменения. " + e.getMessage(), e);
        }

    }

    @Override
    public void update(EntityCategory entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}