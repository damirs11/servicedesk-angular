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
import com.hp.itsm.api.interfaces.IServiceLevelAgreement;
import com.hp.itsm.api.interfaces.IServicecall;
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
    @Autowired
    public IServicecallDao(HpApi api,
                           IPersonDao iPersonDao,
                           IWorkgroupDao iWorkgroupDao,
                           IImpactDao iImpactDao,
                           IConfigurationItemDao iConfigurationItemDao,
                           IFolderDao iFolderDao,
                           IStatusServicecallDao iStatusServicecallDao,
                           IServicecallCategoryDao iServicecallCategoryDao, IOrganizationDao iOrganizationDao, IServiceLevelAgreementDao iServiceLevelAgreementDao, IServiceDao iServiceDao, IClassificationServicecallDao iClassificationServicecallDao, IScClosureCodeDao iScClosureCodeDao) {
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
    }

    @Override
    public long create(ServiceCall entity) {
        IServicecall servicecall = api.getSdClient().sd_session().getServicecallHome().openNewServicecall();
        IStatusServicecall status = iStatusServicecallDao.read(entity.getStatus().getId());
        IImpact priority = iImpactDao.read(entity.getPriority().getId());
        IWorkgroup workgroup = iWorkgroupDao.read(entity.getAssignment().getWorkgroup().getId());
        IPerson executor = iPersonDao.read(entity.getAssignment().getExecutor().getId());
        IServiceCallCategory category = iServicecallCategoryDao.read(entity.getCategory().getId());
        IClassificationSer iClassificationSer = iClassificationServicecallDao.read(entity.getClassification().getId());
        IPerson caller = iPersonDao.read(entity.getCaller().getId());
        IOrganization iOrganization = iOrganizationDao.read(entity.getCaller().getOrganization().getId());
        IPerson requestor = iPersonDao.read(entity.getInitiator().getId());
        IServiceLevelAgreement iServiceLevelAgreement = iServiceLevelAgreementDao.read(entity.getServiceLevelAgreement().getId());
        IService iService = iServiceDao.read(entity.getServiceLevelAgreement().getService().getId());
        IConfigurationItem iConfigurationItem = entity.getConfigurationItem() != null ? iConfigurationItemDao.read(entity.getConfigurationItem().getId()) : null;
        IImpact iImpact = iImpactDao.read(entity.getPriority().getId());
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
        servicecall.setDeadline(DateUtils.toSDDate(entity.getDeadline()));
        servicecall.getAssignment().setAssWorkgroup(workgroup);
        servicecall.getAssignment().setAssigneePerson(executor);
        servicecall.getAssignment().transfer();
        servicecall.setCategory(category);
        servicecall.setClassification(iClassificationSer);
        servicecall.setService(iService);
        servicecall.setSLA(iServiceLevelAgreement);
        servicecall.setImpact(iImpact);
        servicecall.setFolder(iFolder);
        servicecall.setClosureCode(iScClosureCode);
        servicecall.setConfigurationItem(iConfigurationItem);
        servicecall.save();
        return servicecall.getID();
    }

    @Override
    public IServicecall read(long id) {
       return api.getSdClient().sd_session().getServicecallHome().openServicecall(id);
    }

    @Override
    public void update(ServiceCall entity, Set<String> fields) {

    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}
