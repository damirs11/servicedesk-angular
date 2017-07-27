package ru.it.sd.service.external;

import com.hp.itsm.api.interfaces.IWorkgroup;
import com.hp.itsm.api.interfaces.IWorkorder;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import ru.it.sd.model.Workorder;
import ru.it.sd.service.SecurityService;

import java.util.Set;

/**
 * Created by user on 27.07.2017.
 */
public class IWorkgroupService implements ExternalCRUD<Workorder, IWorkgroup>{
    @Autowired
    SecurityService securityService;


    @Override
    public IWorkgroup read(long id) {
        SdClientBean sdClientBean = securityService.getDynamicAuthentication().getSdClient();
        IWorkgroup iWorkgroup = sdClientBean.sd_session().getWorkgroupHome().openWorkgroup(id);
        return iWorkgroup;
    }

    @Override
    public long create(Workorder entity) {

        return 0;
    }

    @Override
    public long update(Workorder entity) {
        return 0;
    }

    @Override
    public long patch(Workorder entity, Set<String> fields) {
        return 0;
    }

    @Override
    public void delete(Workorder entity) {

    }
}
