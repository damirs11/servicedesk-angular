package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IWorkgroup;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.Workgroup;

/**
 * Created by user on 27.07.2017.
 */
@Repository
public class IWorkgroupDao implements HpCrudDao<Workgroup, IWorkgroup> {

    @Autowired
    private HpApi api;

    @Override
    public long create(Workgroup entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IWorkgroup read(long id) {
        try {
            return api.getSdClient().sd_session().getWorkgroupHome().openWorkgroup(id);
        } catch (Exception e){
            throw new ServiceException("Не найдена группа "+e.getMessage(),e);
        }
    }

    @Override
    public void update(Workgroup entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}