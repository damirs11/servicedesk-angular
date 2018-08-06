package ru.it.sd.hp.workorder;

import com.hp.itsm.api.interfaces.*;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.hp.HpApi;
import ru.it.sd.hp.HpCrudDao;
import ru.it.sd.hp.IFolderDao;
import ru.it.sd.hp.IOrganizationDao;
import ru.it.sd.hp.IPersonDao;
import ru.it.sd.hp.IWorkgroupDao;
import ru.it.sd.hp.utils.DateUtils;
import ru.it.sd.hp.change.IChangeDao;
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
    private IChangeDao iChangeDao;
    @Autowired
    private IPersonDao iPersonDao;
    @Autowired
    private IWorkorderStatusDao iWorkorderStatusDao;
    @Autowired
    private IWorkorderCategoryDao iWorkorderCategoryDao;
    @Autowired
    private IWorkgroupDao iWorkgroupDao;
    @Autowired
    private IOrganizationDao iOrganizationDao;
    @Autowired
    private IWorkorderClosureCodeDao iWorkorderClosureCodeDao;
    @Autowired
    private IFolderDao iFolderDao;

    @Override
    public long create(Workorder entity) {
        SdClientBean sdClientBean = api.getSdClient();

        IWorkorder iWorkorder = sdClientBean.sd_session().getWorkorderHome().openNewWorkorder(281495075961055L);

        IWorkorderStatus iStatus = iWorkorderStatusDao.read(entity.getStatus().getId());
        IWorkorderCategory iWorkorderCategory = iWorkorderCategoryDao.read(entity.getCategory().getId());
        IWorkgroup iWorkgroup = iWorkgroupDao.read(entity.getAssignment().getWorkgroup().getId());
        IPerson initiator = iPersonDao.read(entity.getInitiator().getId());
        IPerson assignee = iPersonDao.read(entity.getAssignment().getExecutor().getId());


        IOrganization iOrganization = entity.getOrganization() != null ? iOrganizationDao.read(entity.getOrganization().getId()) : null;
        IWorkorderClosureCode iWorkorderClosureCode = entity.getClosureCode() != null ? iWorkorderClosureCodeDao.read(entity.getClosureCode().getId()) : null;
        IFolder iFolder = entity.getFolder() != null ? iFolderDao.read(entity.getFolder().getId()) : null;
        Double deadline = entity.getDeadline() != null ? DateUtils.toSDDate(entity.getDeadline()) : null;
        IChange iChange = entity.getChange() != null ? iChangeDao.read(entity.getChange().getId()) : null;

        iWorkorder.setStatus(iStatus);
        iWorkorder.setInformation(entity.getSubject());
        iWorkorder.setDescription(entity.getDescription());
        iWorkorder.setCategory(iWorkorderCategory);
        iWorkorder.setRequestor(initiator);

        iWorkorder.setWorkorderOrganization1(iOrganization);
        iWorkorder.setClosureCode(iWorkorderClosureCode);
        iWorkorder.setFolder(iFolder);
        iWorkorder.setDeadline(deadline);
        iWorkorder.setChange(iChange);

        iWorkorder.getAssignment().setAssWorkgroup(iWorkgroup);
        iWorkorder.getAssignment().setAssigneePerson(assignee);
        iWorkorder.getAssignment().transfer();
        iWorkorder.save();

        return iWorkorder.getOID();
    }

    @Override
    public IWorkorder read(long id) {
        return api.getSdClient().sd_session().getWorkorderHome().openWorkorder(Long.valueOf(id));
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Workorder entity, Set<String> fields) {
        IWorkorder iWorkorder = read(entity.getId());
        if(fields.contains("status")) {
            IWorkorderStatus iStatus = iWorkorderStatusDao.read(entity.getStatus().getId());
            iWorkorder.setStatus(iStatus);
        }
        if(fields.contains("category")) {
            IWorkorderCategory iWorkorderCategory = iWorkorderCategoryDao.read(entity.getCategory().getId());
            iWorkorder.setCategory(iWorkorderCategory);
        }
        if(fields.contains("assignment")) {
            if (entity.getAssignment().getWorkgroup() != null) {
                IWorkgroup iWorkgroup = iWorkgroupDao.read(entity.getAssignment().getWorkgroup().getId());
                iWorkorder.getAssignment().setAssWorkgroup(iWorkgroup);
            }
            if(entity.getAssignment().getExecutor() != null){
                IPerson executor = iPersonDao.read(entity.getAssignment().getExecutor().getId());
                iWorkorder.getAssignment().setAssigneePerson(executor);
            }
            iWorkorder.getAssignment().transfer();
        }
        if(fields.contains("initiator")){
            IPerson initiator = iPersonDao.read(entity.getInitiator().getId());
            iWorkorder.setRequestor(initiator);
        }
        if(fields.contains("subject")) {
            iWorkorder.setDescription(entity.getSubject());
        }
        if(fields.contains("description")) {
            iWorkorder.setInformation(entity.getSubject());
        }
        if(fields.contains("organization")){
            IOrganization iOrganization = entity.getOrganization() != null ? iOrganizationDao.read(entity.getOrganization().getId()) : null;
            iWorkorder.setWorkorderOrganization1(iOrganization);
        }
        if(fields.contains("closureCode")){
            IWorkorderClosureCode iWorkorderClosureCode = entity.getClosureCode() != null ? iWorkorderClosureCodeDao.read(entity.getClosureCode().getId()) : null;
            iWorkorder.setClosureCode(iWorkorderClosureCode);
        }
        if(fields.contains("folder")){
            IFolder iFolder = entity.getFolder() != null ? iFolderDao.read(entity.getFolder().getId()) : null;
            iWorkorder.setFolder(iFolder);
        }
        if(fields.contains("deadline")){
            Double deadline = entity.getDeadline() != null ? DateUtils.toSDDate(entity.getDeadline()) : null;
            iWorkorder.setDeadline(deadline);
        }
        if(fields.contains("change")){
            IChange iChange = entity.getChange() != null ? iChangeDao.read(entity.getChange().getId()) : null;
            iWorkorder.setChange(iChange);
        }
        if(fields.contains("commentToExecutor")){
            iWorkorder.setWorkorderText1(entity.getCommentToExecutor());
        }
        if(fields.contains("commentToInitiator")){
            iWorkorder.setWorkorderText2(entity.getCommentToInitiator());
        }
        if(fields.contains("solution")){
            iWorkorder.setWor4k1(entity.getSolution());
        }
        iWorkorder.save();
    }
}

