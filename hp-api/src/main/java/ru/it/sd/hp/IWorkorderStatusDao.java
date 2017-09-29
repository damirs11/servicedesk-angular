package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IWorkorderStatus;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;

/**
 * Created by user on 27.07.2017.
 */
@Repository
public class IWorkorderStatusDao {

    @Autowired
    private HpApi api;

    public long create(IWorkorderStatus entity) {
        throw new UnsupportedOperationException();
    }

    //@Override
    public long create(Object entity) {
        throw new UnsupportedOperationException();
    }

    //@Override
    public IWorkorderStatus read(long id) {
        try{
            return api.getSdClient().sd_session().getWorkorderStatusHome().openWorkorderStatus(id);
        } catch (Exception e){
            throw new ServiceException("Не найден статус наряда "+e.getMessage(),e);
        }
    }

    //@Override
    public void update(Object entity) {
        throw new UnsupportedOperationException();
    }

    //@Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}