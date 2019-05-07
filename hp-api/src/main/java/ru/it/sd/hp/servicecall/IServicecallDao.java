package ru.it.sd.hp.servicecall;


import com.hp.itsm.api.interfaces.IClassificationSer;
import com.hp.itsm.api.interfaces.IConfigurationItem;
import com.hp.itsm.api.interfaces.IFolder;
import com.hp.itsm.api.interfaces.IImpact;
import com.hp.itsm.api.interfaces.IOrganization;
import com.hp.itsm.api.interfaces.IPerson;
import com.hp.itsm.api.interfaces.IScClosureCode;
import com.hp.itsm.api.interfaces.IService;
import com.hp.itsm.api.interfaces.IServiceCallCategory;
import com.hp.itsm.api.interfaces.IServicecall;
import com.hp.itsm.api.interfaces.IServicecallCode1;
import com.hp.itsm.api.interfaces.IStatusServicecall;
import com.hp.itsm.api.interfaces.IWorkgroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.hp.HpApi;
import ru.it.sd.hp.HpCrudDao;
import ru.it.sd.hp.IConfigurationItemDao;
import ru.it.sd.hp.IFolderDao;
import ru.it.sd.hp.IImpactDao;
import ru.it.sd.hp.IOrganizationDao;
import ru.it.sd.hp.IPersonDao;
import ru.it.sd.hp.IServiceDao;
import ru.it.sd.hp.IServiceLevelAgreementDao;
import ru.it.sd.hp.IWorkgroupDao;
import ru.it.sd.hp.utils.DateUtils;
import ru.it.sd.model.ServiceCall;

import java.util.Set;

@Repository
public class IServicecallDao implements HpCrudDao<ServiceCall, IServicecall> {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(IServicecallDao.class);

    private final HpApi api;
    private final IPersonDao iPersonDao;
    private final IWorkgroupDao iWorkgroupDao;
    private final IImpactDao iImpactDao;
    private final IConfigurationItemDao iConfigurationItemDao;
    private final IFolderDao iFolderDao;
    private final IStatusServicecallDao iStatusServicecallDao;
    private final IServicecallCategoryDao iServicecallCategoryDao;
    private final IOrganizationDao iOrganizationDao;
    private final IServiceLevelAgreementDao iServiceLevelAgreementDao;
    private final IServiceDao iServiceDao;
    private final IClassificationServicecallDao iClassificationServicecallDao;
    private final IScClosureCodeDao iScClosureCodeDao;
    private final IServicecallCode1Dao iServicecallCode1Dao;
    @Autowired
    public IServicecallDao(HpApi api,
                           IPersonDao iPersonDao,
                           IWorkgroupDao iWorkgroupDao,
                           IImpactDao iImpactDao,
                           IConfigurationItemDao iConfigurationItemDao,
                           IFolderDao iFolderDao,
                           IStatusServicecallDao iStatusServicecallDao,
                           IServicecallCategoryDao iServicecallCategoryDao, IOrganizationDao iOrganizationDao, IServiceLevelAgreementDao iServiceLevelAgreementDao, IServiceDao iServiceDao, IClassificationServicecallDao iClassificationServicecallDao, IScClosureCodeDao iScClosureCodeDao, IServicecallCode1Dao iServicecallCode1Dao) {
        this.api = api;
        this.iPersonDao = iPersonDao;
        this.iWorkgroupDao = iWorkgroupDao;
        this.iImpactDao = iImpactDao;
        this.iConfigurationItemDao = iConfigurationItemDao;
        this.iFolderDao = iFolderDao;
        this.iStatusServicecallDao = iStatusServicecallDao;
        this.iServicecallCategoryDao = iServicecallCategoryDao;
        this.iOrganizationDao = iOrganizationDao;
        this.iServiceLevelAgreementDao = iServiceLevelAgreementDao;
        this.iServiceDao = iServiceDao;
        this.iClassificationServicecallDao = iClassificationServicecallDao;
        this.iScClosureCodeDao = iScClosureCodeDao;
        this.iServicecallCode1Dao = iServicecallCode1Dao;
    }

    @Override
    public long create(ServiceCall entity) {
        IServicecall servicecall = api.getSdClient().sd_session().getServicecallHome().openNewServicecall();
        IStatusServicecall status = entity.getStatus() != null ? iStatusServicecallDao.read(entity.getStatus().getId()) : null;
        IImpact priority = entity.getPriority() != null ? iImpactDao.read(entity.getPriority().getId()) : null;
        IWorkgroup workgroup = null;
        IPerson executor = null;
        if (entity.getAssignment() != null) {
            workgroup = iWorkgroupDao.read(entity.getAssignment().getWorkgroup().getId());
            executor = entity.getAssignment().getExecutor() != null ? iPersonDao.read(entity.getAssignment().getExecutor().getId()) : null;
        }
        IServiceCallCategory category = entity.getCategory() != null ? iServicecallCategoryDao.read(entity.getCategory().getId()) : null;
        IClassificationSer iClassificationSer = entity.getClassification() != null ? iClassificationServicecallDao.read(entity.getClassification().getId()) : null;
        IPerson caller = entity.getCaller() != null ? iPersonDao.read(entity.getCaller().getId()) : null;
        IOrganization iOrganization = entity.getOrganization() != null ? iOrganizationDao.read(entity.getOrganization().getId()): null;
        IPerson requestor = entity.getInitiator() != null ? iPersonDao.read(entity.getInitiator().getId()) : null;
        IService iService = entity.getService() != null ? iServiceDao.read(entity.getService().getId()) : null;
        IConfigurationItem iConfigurationItem = entity.getConfigurationItem() != null ? iConfigurationItemDao.read(entity.getConfigurationItem().getId()) : null;
        IImpact iImpact = entity.getPriority() != null ? iImpactDao.read(entity.getPriority().getId()) : null;
        IFolder iFolder = entity.getFolder() != null ? iFolderDao.read(entity.getFolder().getId()) : null;
        IScClosureCode iScClosureCode = entity.getClosureCode() != null ? iScClosureCodeDao.read(entity.getClosureCode().getId()) : null;
        //Заявитель
        servicecall.setCaller(caller);
        servicecall.setCallerOrganization(iOrganization);
        //Инициатор
        servicecall.setRequestor(requestor);
        servicecall.setStatus(status);
        servicecall.setInformation(entity.getSubject());
        servicecall.setDescription(entity.getDescription());
        servicecall.setImpact(priority);
        //servicecall.setDeadline(entity.getDeadline() != null ? DateUtils.toSDDate(entity.getDeadline()) : null);
        servicecall.getAssignment().setAssWorkgroup(workgroup);
        servicecall.getAssignment().setAssigneePerson(executor);
        servicecall.getAssignment().transfer();
        servicecall.setCategory(category);
        servicecall.setClassification(iClassificationSer);
        servicecall.setService(iService);
        servicecall.setImpact(iImpact);
        servicecall.setFolder(iFolder);
        servicecall.setClosureCode(iScClosureCode);
        servicecall.setConfigurationItem(iConfigurationItem);
        servicecall.save();
        return servicecall.getOID();
    }

    @Override
    public IServicecall read(long id) {
       return api.getSdClient().sd_session().getServicecallHome().openServicecall(Long.valueOf(id));
    }

    @Override
    public void update(ServiceCall entity, Set<String> fields) {
        IServicecall servicecall = read(entity.getId());
        if (fields.contains("assignment")) {
            if(entity.getAssignment().getWorkgroup() != null){
                IWorkgroup workgroup = iWorkgroupDao.read(entity.getAssignment().getWorkgroup().getId());
                servicecall.getAssignment().setAssWorkgroup(workgroup);
            }
            if(entity.getAssignment().getExecutor() != null) {
                IPerson executor = iPersonDao.read(entity.getAssignment().getExecutor().getId());
                servicecall.getAssignment().setAssigneePerson(executor);
            }
            servicecall.getAssignment().transfer();
        }
        if (fields.contains("folder")){
            IFolder iFolder = iFolderDao.read(entity.getFolder().getId());
            servicecall.setFolder(iFolder);
        }
        if (fields.contains("status")){
            IStatusServicecall status = iStatusServicecallDao.read(entity.getStatus().getId());
            servicecall.setStatus(status);
        }
        if (fields.contains("category")){
            IServiceCallCategory category = iServicecallCategoryDao.read(entity.getCategory().getId());
            servicecall.setCategory(category);
        }
        if (fields.contains("classification")){
            IClassificationSer iClassificationSer = iClassificationServicecallDao.read(entity.getClassification().getId());
            servicecall.setClassification(iClassificationSer);
        }
        if (fields.contains("closureCode")){
            IScClosureCode iScClosureCode = iScClosureCodeDao.read(entity.getClosureCode().getId());
            servicecall.setClosureCode(iScClosureCode);
        }
        if (fields.contains("solution")){
            servicecall.setSolution(entity.getSolution());
        }
        if (fields.contains("initiator")){
            IPerson requestor = iPersonDao.read(entity.getInitiator().getId());
            servicecall.setRequestor(requestor);
        }
        if (fields.contains("caller")){
            IPerson caller = iPersonDao.read(entity.getCaller().getId());
            servicecall.setCaller(caller);
        }
        if (fields.contains("organization")){
            IOrganization iOrganization = iOrganizationDao.read(entity.getOrganization().getId());
            servicecall.setCallerOrganization(iOrganization);
        }
        if (fields.contains("serviceLevelAgreement")){
            //todo уточнить
        }
        if (fields.contains("extId")){
            servicecall.setSerShorttext10(entity.getExtId());
        }
        if (fields.contains("configurationItem")){
            IConfigurationItem configurationItem = iConfigurationItemDao.read(entity.getConfigurationItem().getId());
            servicecall.setConfigurationItem(configurationItem);
        }
        if (fields.contains("subject")){
            servicecall.setDescription(entity.getSubject());
        }
        if (fields.contains("description")){
            servicecall.setInformation(entity.getDescription());
        }
        if (fields.contains("priority")){
            IImpact impact = iImpactDao.read(entity.getPriority().getId());
            servicecall.setImpact(impact);
        }
        if (fields.contains("deadline")){
            servicecall.setDeadline(DateUtils.toSDDate(entity.getDeadline()));
        }
        if (fields.contains("resolvedDate")){
            servicecall.setActualFinish(DateUtils.toSDDate(entity.getResolvedDate()));
        }
        if (fields.contains("closureDate")){
            IScClosureCode closureCode = iScClosureCodeDao.read(entity.getClosureCode().getId());
            servicecall.setClosureCode(closureCode);
        }
        if (fields.contains("expired")){
            servicecall.setSerBoolean1(entity.getExpired());
        }
        if (fields.contains("expiredBy")){
            servicecall.setSerShorttext4(entity.getExpiredBy());
        }
        if (fields.contains("newDeadline")){
            servicecall.setServicecallDate1(DateUtils.toSDDate(entity.getNewDeadline()));
        }
        if (fields.contains("newDeadlineReason")){
            servicecall.setServicecallText11(entity.getNewDeadlineReason());
        }
        if (fields.contains("executorHead")){
            IPerson person = iPersonDao.read(entity.getExecutorHead().getId());
            servicecall.setScPerson1(person);
        }
        if (fields.contains("errorHandling")){
            servicecall.setSerBoolean10(entity.getErrorHandling());
        }
        if (fields.contains("functionalEscalation")){
            servicecall.setSerBoolean12(entity.getFunctionalEscalation());
        }
        if (fields.contains("mark")){
            IServicecallCode1 servicecallCode1 = iServicecallCode1Dao.read(entity.getMark().getId());
            servicecall.setServicecallCode1(servicecallCode1);
        }
        if (fields.contains("commentToInitiator")){
            servicecall.setWorkaround(entity.getCommentToInitiator());
        }
        if (fields.contains("commentToExecutor")){
            servicecall.setSer4k1(entity.getCommentToExecutor());
        }
        servicecall.save();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}
