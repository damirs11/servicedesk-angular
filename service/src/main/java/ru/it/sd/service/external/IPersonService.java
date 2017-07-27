package ru.it.sd.service.external;

import com.hp.itsm.api.interfaces.IPerson;
import com.hp.itsm.api.interfaces.IWorkgroup;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import ru.it.sd.model.Workorder;
import ru.it.sd.service.SecurityService;

import java.util.Set;

/**
 * Created by user on 27.07.2017.
 */
public class IPersonService implements ExternalCRUD<Workorder, IPerson>{
    @Autowired
    SecurityService securityService;


    @Override
    public IPerson read(long id) {
        SdClientBean sdClientBean = securityService.getDynamicAuthentication().getSdClient();
        IPerson iPerson = sdClientBean.sd_session().getPersonHome().openPerson(id);
        return iPerson;
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
