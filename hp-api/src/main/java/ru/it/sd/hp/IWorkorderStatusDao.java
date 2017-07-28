package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IWorkorderStatus;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        SdClientBean sdClientBean = api.getSdClient();
        return sdClientBean.sd_session().getWorkorderStatusHome().openWorkorderStatus(id);
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