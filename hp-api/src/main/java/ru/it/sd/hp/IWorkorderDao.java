package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.*;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.model.Workorder;

import java.util.Set;

/**
 * Created by user on 27.07.2017.
 */
@Repository
public class IWorkorderDao implements HpCrudDao<Workorder, IWorkorder> {

    @Autowired
    private HpApi api;
    @Autowired
    private IPersonDao iPersonDao;
    @Autowired
    private IWorkorderStatusDao iWorkorderStatusDao;
    @Autowired
    private IWorkorderCategoryDao iWorkorderCategoryDao;
    @Autowired
    private IWorkgroupDao iWorkgroupDao;

    @Override
    public long create(Workorder entity) {
        SdClientBean sdClientBean = api.getSdClient();

        IWorkorder iWorkorder = sdClientBean.sd_session().getWorkorderHome().openNewWorkorder(281495075961055L);

        IWorkgroup iWorkgroup = iWorkgroupDao.read(entity.getAssignment().getWorkgroup().getId());
        IPerson initiator = iPersonDao.read(entity.getInitiator().getId());
        IPerson assignee = iPersonDao.read(entity.getAssignment().getExecutor().getId());
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
        return api.getSdClient().sd_session().getWorkorderHome().openWorkorder(Long.valueOf(id));
    }

    @Override
    public void update(Workorder entity) {
        SdClientBean sdClientBean = api.getSdClient();

        IWorkorder iWorkorder = read(entity.getId());

        IWorkgroup iWorkgroup = iWorkgroupDao.read(entity.getAssignment().getWorkgroup().getId());
        IPerson initiator = iPersonDao.read(entity.getInitiator().getId());
        IPerson assignee = iPersonDao.read(entity.getAssignment().getExecutor().getId());
        IWorkorderStatus iStatus = iWorkorderStatusDao.read(entity.getStatus().getId());
        IWorkorderCategory iWorkorderCategory = iWorkorderCategoryDao.read(entity.getCategory().getId());

        iWorkorder.setStatus(iStatus);
        iWorkorder.setInformation(entity.getSubject());
        iWorkorder.setDescription(entity.getDescription());
        iWorkorder.setCategory(iWorkorderCategory);

        iWorkorder.setRequestor(initiator);
        iWorkorder.getAssignment().setAssWorkgroup(iWorkgroup);
        iWorkorder.getAssignment().setAssigneePerson(assignee);
        iWorkorder.getAssignment().transfer();
        iWorkorder.save();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    public void patch(Workorder entity, Set<String> fields) {
        IWorkorder iWorkorder = read(entity.getId());
        if(fields.contains("status")) {
            IWorkorderStatus iStatus = iWorkorderStatusDao.read(entity.getStatus().getId());
            iWorkorder.setStatus(iStatus);
        }
        if(fields.contains("subject")) {
            iWorkorder.setDescription(entity.getSubject());
        }
        if(fields.contains("description")) {
            iWorkorder.setInformation(entity.getSubject());
        }
        if(fields.contains("category")) {
            IWorkorderCategory iWorkorderCategory = iWorkorderCategoryDao.read(entity.getCategory().getId());
            iWorkorder.setCategory(iWorkorderCategory);
        }
        if(fields.contains("workgroup")){
            IWorkgroup iWorkgroup = iWorkgroupDao.read(entity.getAssignment().getWorkgroup().getId());
            iWorkorder.getAssignment().setAssWorkgroup(iWorkgroup);
        }
        if(fields.contains("executor")){
            IPerson executor = iPersonDao.read(entity.getAssignment().getExecutor().getId());
            iWorkorder.getAssignment().setAssigneePerson(executor);
        }
        if(fields.contains("initiator")){
            IPerson initiator = iPersonDao.read(entity.getInitiator().getId());
            iWorkorder.setRequestor(initiator);
        }
        iWorkorder.getAssignment().transfer();
        iWorkorder.save();
    }
}

