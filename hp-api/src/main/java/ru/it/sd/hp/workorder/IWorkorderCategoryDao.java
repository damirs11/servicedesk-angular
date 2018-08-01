package ru.it.sd.hp.workorder;

import com.hp.itsm.api.interfaces.IWorkorderCategory;
import com.hp.itsm.ssp.beans.WorkorderCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.HpApi;

/**
 * Created by user on 27.07.2017.
 */
//todo
@Repository
public class IWorkorderCategoryDao {

    @Autowired
    private HpApi api;

    public long create(WorkorderCategory entity) {
        throw new UnsupportedOperationException();
    }

    public IWorkorderCategory read(long id) {
        try {
            return  api.getSdClient().sd_session().getWorkorderCategoryHome().openWorkorderCategory(id);
        } catch (Exception e){
            throw new ServiceException("Не найдена категория наряда "+e.getMessage(),e);
        }
    }

    public void update(WorkorderCategory entity) {
        throw new UnsupportedOperationException();
    }

    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}
