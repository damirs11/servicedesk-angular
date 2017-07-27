package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IWorkorderCategory;
import com.hp.itsm.ssp.beans.SdClientBean;
import com.hp.itsm.ssp.beans.WorkorderCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        SdClientBean sdClientBean = api.getSdClient();
        return sdClientBean.sd_session().getWorkorderCategoryHome().openWorkorderCategory(id);
    }

    public void update(WorkorderCategory entity) {
        throw new UnsupportedOperationException();
    }

    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}
