package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.*;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.ChangeDao;
import ru.it.sd.dao.WorkorderDao;
import ru.it.sd.model.Workorder;

/**
 * Created by user on 27.07.2017.
 */
@Repository
public class IWorkorderDao implements HpCrudDao<Workorder, IWorkorder> {

    @Autowired
    private HpApi api;
    @Autowired
    private WorkorderDao workorderDao;
    @Autowired
    private IPersonDao iPersonDao;
    @Autowired
    private IWorkorderStatusDao iWorkorderStatusDao;
    @Autowired
    private IWorkorderCategoryDao iWorkorderCategoryDao;
    @Autowired
    private IOrganizationDao iOrganizationDao;
    @Autowired
    private IWorkgroupDao iWorkgroupDao;

    @Override
    public long create(Workorder entity) {
        SdClientBean sdClientBean = api.getSdClient();

        IWorkorder iWorkorder = sdClientBean.sd_session().getWorkorderHome().openNewWorkorder(281495075961055L);

        IWorkgroup iWorkgroup = iWorkgroupDao.read(entity.getAssWorkgroup().getId());
        IPerson initiator = iPersonDao.read(entity.getInitiator().getId());
        IPerson assignee = iPersonDao.read(entity.getAssigneePerson().getId());
        IWorkorderStatus iStatus = iWorkorderStatusDao.read(entity.getStatus().getId());
        IWorkorderCategory iWorkorderCategory = iWorkorderCategoryDao.read(entity.getCategory().getId());

        iWorkorder.setStatus(iStatus);
        iWorkorder.setInformation(entity.getSubject());
        iWorkorder.setDescription(entity.getDescription());
        iWorkorder.setCategory(iWorkorderCategory);

        iWorkorder.getAssignment().setAssWorkgroup(iWorkgroup);
        iWorkorder.getAssignment().setAssigneePerson(assignee);
        iWorkorder.getAssignment().transfer();
        iWorkorder.save();

        return iWorkorder.getID();
    }

    @Override
    public IWorkorder read(long id) {
        SdClientBean sdClientBean = api.getSdClient();
        Long no = workorderDao.read(id).getNo();
        return sdClientBean.sd_session().getWorkorderHome().openWorkorder(no);
    }

    @Override
    public void update(Workorder entity) {
        SdClientBean sdClientBean = api.getSdClient();

        IWorkorder iWorkorder = read(entity.getId());

        IWorkgroup iWorkgroup = iWorkgroupDao.read(entity.getAssWorkgroup().getId());
        IPerson initiator = iPersonDao.read(entity.getInitiator().getId());
        IPerson assignee = iPersonDao.read(entity.getAssigneePerson().getId());
        IWorkorderStatus iStatus = iWorkorderStatusDao.read(entity.getStatus().getId());
        IWorkorderCategory iWorkorderCategory = iWorkorderCategoryDao.read(entity.getCategory().getId());

        iWorkorder.setStatus(iStatus);
        iWorkorder.setInformation(entity.getSubject());
        iWorkorder.setDescription(entity.getDescription());
        iWorkorder.setCategory(iWorkorderCategory);

        iWorkorder.getAssignment().setAssWorkgroup(iWorkgroup);
        iWorkorder.getAssignment().setAssigneePerson(assignee);
        iWorkorder.getAssignment().transfer();
        iWorkorder.save();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}
