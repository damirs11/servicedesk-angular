package ru.it.sd.service.external;

import com.hp.itsm.api.interfaces.IOrganization;
import com.hp.itsm.api.interfaces.IPerson;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import ru.it.sd.model.Workorder;
import ru.it.sd.service.SecurityService;

import java.util.Set;

/**
 * Created by user on 27.07.2017.
 */
public class IOrganizationService implements ExternalCRUD<Workorder, IOrganization>{
    @Autowired
    SecurityService securityService;


    @Override
    public IOrganization read(long id) {
        SdClientBean sdClientBean = securityService.getDynamicAuthentication().getSdClient();
        IOrganization iOrganization = sdClientBean.sd_session().getOrganizationHome().openOrganization(id);
        return iOrganization;
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
