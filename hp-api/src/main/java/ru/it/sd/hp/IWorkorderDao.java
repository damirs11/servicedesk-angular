package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.*;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.model.Workorder;

/**
 * Created by user on 27.07.2017.
 */
@Repository
public class IWorkorderDao implements HpCrudDao<Workorder, IWorkorder> {

    @Autowired
    private HpApi api;
    @Autowired
    private IPersonDao iPersonService;
    @Autowired
    private IWorkorderStatusDao iWorkorderStatusService;
    @Autowired
    private IWorkorderCategoryDao iWorkorderCategoryService;
    @Autowired
    private IOrganizationDao iOrganizationService;

    @Override
    public long create(Workorder entity) {
        SdClientBean sdClientBean = api.getSdClient();
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
    public IWorkorder read(long id) {
        SdClientBean sdClientBean = api.getSdClient();
        return sdClientBean.sd_session().getWorkorderHome().openWorkorder(id);
    }

    @Override
    public void update(Workorder entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        SdClientBean sdClientBean = api.getSdClient();
        IWorkorder iWorkorder = sdClientBean.sd_session().getWorkorderHome().openWorkorder(id);
        iWorkorder.delete();
    }
}
