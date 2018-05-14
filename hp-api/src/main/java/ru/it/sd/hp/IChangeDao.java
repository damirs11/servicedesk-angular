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
    @Autowired
    private IConfigurationItemDao iConfigurationItemDao;
    @Autowired
    private IChangeCode1Dao iChangeCode1Dao;
    @Autowired
    private IChangeClosureCodeDao iChangeClosureCodeDao;
    @Autowired
    private IStatusChangeDao iStatusChangeDao;
    @Autowired
    private IFolderDao iFolderDao;

    @Override
    public long create(Change entity) {
        SdClientBean sdClientBean = api.getSdClient();
        IChange iChange = sdClientBean.sd_session().getChangeHome().openNewChange(3095920802L);

        IChangeCategory iChangeCategory = iChangeCategoryDao.read(entity.getCategory().getId());
        IClassificationCha iClassificationCha = iClassificationChaDao.read(entity.getClassification().getId());
        IPerson initiator = iPersonDao.read(entity.getInitiator().getId());
        IPerson manager = iPersonDao.read(entity.getManager().getId());
        IPerson executor = iPersonDao.read(entity.getAssignment().getExecutor().getId());
        IWorkgroup workgroup = iWorkgroupDao.read(entity.getAssignment().getWorkgroup().getId());
        IImpact impact = iImpactDao.read(entity.getPriority().getId());

        IConfigurationItem configurationItem = entity.getConfigurationItem() != null ? iConfigurationItemDao.read(entity.getConfigurationItem().getId()): null;
        IChangeCode1 system = entity.getSystem() != null ? iChangeCode1Dao.read(entity.getSystem().getId()): null;
        IChangeClosureCode closureCode = entity.getClosureCode() != null ? iChangeClosureCodeDao.read(entity.getClosureCode().getId()) : null;
        IFolder folder = entity.getFolder() != null ? iFolderDao.read(entity.getFolder().getId()): null;

        //обязательные поля
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

        //необязательные
        iChange.setConfigurationItem(configurationItem);
        iChange.setChangeCode1(system);
        iChange.setClosureCode(closureCode);
        iChange.setFolder(folder);
        iChange.save();

        return iChange.getOID();
    }

    @Override
    public IChange read(long id) {
        return api.getSdClient().sd_session().getChangeHome().openChange(Long.valueOf(id));
    }

    @Override
    public void update(Change entity) {
        throw new UnsupportedOperationException();
    }

    public void patch(Change entity, Set<String> fields) {
        IChange iChange = read(entity.getId());

        if(fields.contains("subject")) {
            iChange.setDescription(entity.getSubject());
        }
        if(fields.contains("description")) {
            iChange.setInformation(entity.getDescription());
        }
        if(fields.contains("solution")) {
            iChange.setSolution(entity.getSolution());
        }
        if(fields.contains("status")){
            IStatusChange iStatusChange = iStatusChangeDao.read(entity.getStatus().getId());
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
        if(fields.contains("closureCode")){
            IChangeClosureCode closureCode = entity.getClosureCode() != null ? iChangeClosureCodeDao.read(entity.getClosureCode().getId()) : null;
            iChange.setClosureCode(closureCode);
        }
        if(fields.contains("deadline")) {
            iChange.setDeadline(DateUtils.toSDDate(entity.getDeadline()));
        }
        if(fields.contains("actualStart")) {
            iChange.setActualStart(DateUtils.toSDDate(entity.getActualStart()));
        }
        if(fields.contains("resolvedDate")) {
            iChange.setActualFinish(DateUtils.toSDDate(entity.getResolvedDate()));
        }
        if(fields.contains("closureDate")) {
            iChange.setLateFinish(DateUtils.toSDDate(entity.getClosureDate()));
        }
        if(fields.contains("planStart")) {
            iChange.setPlanStart(
                    entity.getPlanStart() != null ? DateUtils.toSDDate(entity.getPlanStart()):null
            );
        }
        if(fields.contains("planFinish")) {
            iChange.setPlanFinish(
                    entity.getPlanFinish() != null?
                    DateUtils.toSDDate(entity.getPlanFinish()): null
            );
        }
        if(fields.contains("planDuration")) {
            iChange.setPlanDuration(
                    entity.getPlanDuration() != null ?
                    Double.valueOf(entity.getPlanDuration().getTime())/1000/24/60/60:null
            );
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
        if(fields.contains("assignment")) {
            if(entity.getAssignment().getWorkgroup() != null){
                IWorkgroup workgroup = iWorkgroupDao.read(entity.getAssignment().getWorkgroup().getId());
                iChange.getAssignment().setAssWorkgroup(workgroup);
            }
            if(entity.getAssignment().getExecutor() != null) {
                IPerson executor = iPersonDao.read(entity.getAssignment().getExecutor().getId());
                iChange.getAssignment().setAssigneePerson(executor);
            }
            iChange.getAssignment().transfer();
        }
        if(fields.contains("configurationItem")) {
            IConfigurationItem configurationItem = entity.getConfigurationItem() != null ? iConfigurationItemDao.read(entity.getConfigurationItem().getId()): null;
            iChange.setConfigurationItem(configurationItem);
        }
        if(fields.contains("system")){
            IChangeCode1 system = entity.getSystem() != null ? iChangeCode1Dao.read(entity.getSystem().getId()): null;
            iChange.setChangeCode1(system);
        }
        if(fields.contains("folder")){
            IFolder iFolder = entity.getFolder() != null ?
                    iFolderDao.read(entity.getFolder().getId()):
                    null;
            iChange.setFolder(iFolder);
        }
        if(fields.contains("commentToExecutor")){
            iChange.setChangeText1(entity.getCommentToExecutor());
        }
        if(fields.contains("commentToManager")){
            iChange.setChangeText7(entity.getCommentToManager());
        }
        if(fields.contains("commentToInitiator")){
            iChange.setWorkaround(entity.getCommentToInitiator());
        }
        iChange.save();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}
