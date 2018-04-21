package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IConfigurationItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.ConfigurationItem;

/**
 * Created by nsyhev 29.09.2017
 */
@Repository
public class IConfigurationItemDao implements HpCrudDao<ConfigurationItem, IConfigurationItem> {

    @Autowired
    private HpApi api;


    @Override
    public long create(ConfigurationItem entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IConfigurationItem read(long id) {
        try{
            return api.getSdClient().sd_session().getConfigurationItemHome().openConfigurationItem(Long.valueOf(id));
        }catch (Exception e){
            throw new ServiceException("Не найден", e);
        }
    }

    @Override
    public void update(ConfigurationItem entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}