package ru.it.sd.hp.change;

import com.hp.itsm.api.interfaces.IChangeCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.HpApi;
import ru.it.sd.hp.HpCrudDao;
import ru.it.sd.model.EntityCategory;

import java.util.Set;

/**
 * Created by nsyhev 29.09.2017
 */
@Repository
public class IChangeCategoryDao implements HpCrudDao<EntityCategory, IChangeCategory> {

    @Autowired
    private HpApi api;

    @Override
    public long create(EntityCategory entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IChangeCategory read(long id) {
        try{
            return api.getSdClient().sd_session().getChangeCategoryHome().openChangeCategory(id);
        }catch (Exception e){
            throw new ServiceException("Не найдена категория изменения. " + e.getMessage(), e);
        }

    }

    @Override
    public void update(EntityCategory entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}