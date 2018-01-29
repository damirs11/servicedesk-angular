package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.*;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.hp.Utils.DateUtils;
import ru.it.sd.model.Change;

import java.util.Set;

@Repository
public class IChangeDao implements HpCrudDao<Change, IChange>{

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(IChangeDao.class);

    @Autowired
    private HpApi api;
    @Autowired
    private IPersonDao iPersonDao;
    @Autowired
    private IWorkgroupDao iWorkgroupDao;
    @Autowired
    private IImpactDao iImpactDao;
    @Autowired
    private IChangeCategoryDao iChangeCategoryDao;
    @Autowired
    private IClassificationChaDao iClassificationChaDao;
    @Override
    public long create(Change entity) {
        SdClientBean sdClientBean = api.getSdClient();
        IChange iChange = sdClientBean.sd_session().getChangeHome().openNewChange(3095920802L);

        IChangeCategory iChangeCategory = iChangeCategoryDao.read(entity.getCategory().getId());
        IClassificationCha iClassificationCha = iClassificationChaDao.read(entity.getClassification().getId());
        IPerson initiator = iPersonDao.read(entity.getInitiator().getId());
        IPerson manager = iPersonDao.read(entity.getManager().getId());
        IPerson executor = iPersonDao.read(entity.getExecutor().getId());
        IWorkgroup workgroup = iWorkgroupDao.read(entity.getWorkgroup().getId());
        IImpact impact = iImpactDao.read(entity.getPriority().getId());

        iChange.setCategory(iChangeCategory);
        iChange.setClassification(iClassificationCha);
        iChange.setDeadline(DateUtils.toSDDate(entity.getDeadline()));
        iChange.setDescription(entity.getDescription());
        iChange.setInformation(entity.getSubject());
        iChange.setManager(manager);
        iChange.setRequestor(initiator);
        iChange.setImpact(impact);
        iChange.getAssignment().setAssWorkgroup(workgroup);
        iChange.getAssignment().setAssigneePerson(executor);
        iChange.getAssignment().transfer();
        iChange.save();

        return iChange.getOID();
    }

    @Override
    public IChange read(long id) {
        return api.getSdClient().sd_session().getChangeHome().openChange(Long.valueOf(id));
    }

    @Override
    public void update(Change entity) {
        IChange iChange = read(entity.getId());

        IChangeCategory iChangeCategory = iChangeCategoryDao.read(entity.getCategory().getId());
        IClassificationCha iClassificationCha = iClassificationChaDao.read(entity.getClassification().getId());
        IPerson initiator = iPersonDao.read(entity.getInitiator().getId());
        IPerson manager = iPersonDao.read(entity.getManager().getId());
        IPerson executor = iPersonDao.read(entity.getExecutor().getId());
        IWorkgroup workgroup = iWorkgroupDao.read(entity.getWorkgroup().getId());
        IImpact impact = iImpactDao.read(entity.getPriority().getId());

        iChange.setCategory(iChangeCategory);
        iChange.setClassification(iClassificationCha);
        iChange.setDeadline(DateUtils.toSDDate(entity.getDeadline()));
        iChange.setDescription(entity.getSubject());
        iChange.setInformation(entity.getDescription());
        iChange.setManager(manager);
        iChange.setRequestor(initiator);
        iChange.setImpact(impact);
        iChange.getAssignment().setAssWorkgroup(workgroup);
        iChange.getAssignment().setAssigneePerson(executor);
        iChange.getAssignment().transfer();
        iChange.save();

    }

    public void patch(Change entity, Set<String> fields) {
        IChange iChange = read(entity.getId());

        if(fields.contains("status")){
            IStatusChange iStatusChange = api.getSdClient().sd_session().getStatusChangeHome().openStatusChange(entity.getStatus().getId());
            iChange.setStatus(iStatusChange);
        }
        if(fields.contains("category")){
            IChangeCategory iChangeCategory = iChangeCategoryDao.read(entity.getCategory().getId());
            iChange.setCategory(iChangeCategory);
        }
        if(fields.contains("classification")){
            IClassificationCha iClassificationCha = iClassificationChaDao.read(entity.getClassification().getId());
            iChange.setClassification(iClassificationCha);
        }
        if(fields.contains("deadline")) {
            iChange.setDeadline(DateUtils.toSDDate(entity.getDeadline()));
        }
        if(fields.contains("subject")) {
            iChange.setDescription(entity.getSubject());
        }
        if(fields.contains("description")) {
            iChange.setInformation(entity.getDescription());
        }
        if(fields.contains("manager")){
            IPerson manager = iPersonDao.read(entity.getManager().getId());
            iChange.setManager(manager);
        }
        if(fields.contains("initiator")){
            IPerson initiator = iPersonDao.read(entity.getInitiator().getId());
            iChange.setRequestor(initiator);
        }
        if(fields.contains("priority")) {
            IImpact impact = iImpactDao.read(entity.getPriority().getId());
            iChange.setImpact(impact);
        }
        if(fields.contains("assWorkgroup")) {
            IWorkgroup workgroup = iWorkgroupDao.read(entity.getWorkgroup().getId());
            iChange.getAssignment().setAssWorkgroup(workgroup);
        }
        if(fields.contains("executor")) {
            IPerson executor = iPersonDao.read(entity.getExecutor().getId());
            iChange.getAssignment().setAssigneePerson(executor);
        }
        iChange.getAssignment().transfer();
        iChange.save();

    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}
