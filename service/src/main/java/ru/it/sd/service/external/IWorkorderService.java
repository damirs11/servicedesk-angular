package ru.it.sd.service.external;

import com.hp.itsm.api.interfaces.*;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import ru.it.sd.model.Workorder;
import ru.it.sd.service.SecurityService;

import java.util.Set;

/**
 * Created by user on 27.07.2017.
 */
public class IWorkorderService implements ExternalCRUD<Workorder, IWorkorder> {
    @Autowired
    SecurityService securityService;
    @Autowired
    IPersonService iPersonService;
    @Autowired
    IWorkorderStatusService iWorkorderStatusService;
    @Autowired
    IWorkorderCategoryService iWorkorderCategoryService;
    @Autowired
    IOrganizationService iOrganizationService;
    @Autowired
    IWorkgroupService iWorkgroupService;

    @Override
    public IWorkorder read(long id) {
        SdClientBean sdClientBean = securityService.getDynamicAuthentication().getSdClient();
        IWorkorder iWorkorder = sdClientBean.sd_session().getWorkorderHome().openWorkorder(id);
        return iWorkorder;
    }

    @Override
    public long create(Workorder entity) {
        SdClientBean sdClientBean = securityService.getDynamicAuthentication().getSdClient();
        //Создание нового наряда
        IWorkorder iWorkorder = sdClientBean.sd_session().getWorkorderHome().openNewWorkorder();
        //Открытие инициатора
        IPerson initiator = iPersonService.read(entity.getInitiator().getId());
        //Открытие инициатора
        IPerson assignee = iPersonService.read(entity.getAssigneePerson().getId());
        //Открытие статуса
        IWorkorderStatus iWorkorderStatus = iWorkorderStatusService.read(entity.getStatus().getId());
        //Открытие категории
        IWorkorderCategory iWorkorderCategory = iWorkorderCategoryService.read(entity.getCategory().getId());
        //Открытие организации
        IOrganization iOrganization = iOrganizationService.read(entity.getInitiator().getOrganization().getId());

        // Открытие рабочей группы(Доработать дао)
        IWorkgroup iWorkgroup = null;//iWorkgroupService.read(entity.getAssigneePerson().);

        //Заполнение наряда

        iWorkorder.setRequestor(initiator);
        iWorkorder.setInformation(entity.getDescription());
        iWorkorder.setDescription(entity.getSubject());
        iWorkorder.setStatus(iWorkorderStatus);
        iWorkorder.setCategory(iWorkorderCategory);
        iWorkorder.setWorkorderOrganization1(iOrganization);
        iWorkorder.getAssignment().setAssWorkgroup(iWorkgroup);
        iWorkorder.getAssignment().setAssigneePerson(assignee);
        iWorkorder.getAssignment().transfer();
        iWorkorder.save();
        return iWorkorder.getID();
    }

    @Override
    public long update(Workorder entity) {
        return 0;
    }

    /*@Override
    public long patch(Workorder entity, Set<String> fields) {
        return 0;
    }*/

    @Override
    public void delete(Workorder entity) {
        SdClientBean sdClientBean = securityService.getDynamicAuthentication().getSdClient();
        //Создание нового наряда
        IWorkorder iWorkorder = sdClientBean.sd_session().getWorkorderHome().openWorkorder(entity.getId());
        iWorkorder.delete();
    }
}
