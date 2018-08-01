package ru.it.sd.hp.problem;

import com.hp.itsm.api.interfaces.IClassProblem;
import com.hp.itsm.api.interfaces.IConfigurationItem;
import com.hp.itsm.api.interfaces.IFolder;
import com.hp.itsm.api.interfaces.IImpact;
import com.hp.itsm.api.interfaces.IPerson;
import com.hp.itsm.api.interfaces.IProblem;
import com.hp.itsm.api.interfaces.IProblemCategory;
import com.hp.itsm.api.interfaces.IProblemClosureCode;
import com.hp.itsm.api.interfaces.IStatusProblem;
import com.hp.itsm.api.interfaces.IWorkgroup;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.hp.HpApi;
import ru.it.sd.hp.HpCrudDao;
import ru.it.sd.hp.IConfigurationItemDao;
import ru.it.sd.hp.IFolderDao;
import ru.it.sd.hp.IImpactDao;
import ru.it.sd.hp.IPersonDao;
import ru.it.sd.hp.IWorkgroupDao;
import ru.it.sd.hp.utils.DateUtils;
import ru.it.sd.model.Problem;

@Repository
public class IProblemDao implements HpCrudDao<Problem, IProblem> {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(IProblemDao.class);

    @Autowired
    private HpApi api;
    @Autowired
    private IPersonDao iPersonDao;
    @Autowired
    private IWorkgroupDao iWorkgroupDao;
    @Autowired
    private IImpactDao iImpactDao;
    @Autowired
    private IStatusProblemDao iStatusProblemDao;
    @Autowired
    private IProblemCategoryDao iProblemCategoryDao;
    @Autowired
    private IClassProblemDao iClassProblemDao;
    @Autowired
    private IProblemClosureCodeDao iProblemClosureCodeDao;
    @Autowired
    private IConfigurationItemDao iConfigurationItemDao;
    @Autowired
    private IFolderDao iFolderDao;

    @Override
    public long create(Problem entity) {
        SdClientBean sdClientBean = api.getSdClient();
        IProblem iProblem = sdClientBean.sd_session().getProblemHome().openNewProblem();
        convertToApiModel(entity, iProblem);
        iProblem.save();
        return iProblem.getOID();
    }

    @Override
    public IProblem read(long id) {
        return api.getSdClient().sd_session().getProblemHome().openProblem(Long.valueOf(id));
    }

    @Override
    public void update(Problem entity) {
        IProblem iProblem = read(entity.getId());
        convertToApiModel(entity, iProblem);
        iProblem.save();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Перевод модели в Api модель
     * @param entity сущность с клиента
     * @param iProblem сущность api
     */
    private void convertToApiModel(Problem entity, IProblem iProblem) {
        if (entity.getStatus() != null) {
            IStatusProblem iStatusProblem = iStatusProblemDao.read(entity.getStatus().getId());
            iProblem.setStatus(iStatusProblem);
        }
        if (entity.getInitiator() != null) {
            IPerson initiator = iPersonDao.read(entity.getInitiator().getId());
            iProblem.setRequestor(initiator);
        }
        if (entity.getConfigurationItem() != null) {
            IConfigurationItem iConfigurationItem = iConfigurationItemDao.read(entity.getConfigurationItem().getId());
            iProblem.setConfigurationItem(iConfigurationItem);
        }
        if (entity.getSubject() != null) {
            iProblem.setDescription(entity.getSubject());
        }
        if (entity.getDescription() != null) {
            iProblem.setInformation(entity.getDescription());
        }
        if (entity.getLogLinks() != null) {
            iProblem.setProblemText4(entity.getLogLinks());
        }
        if (entity.getJiraLink() != null) {
            iProblem.setProblemText1(entity.getJiraLink());
        }
        if (entity.getToVendor() != null) {
            iProblem.setProblemText3(entity.getToVendor());
        }
        if (entity.getWorkaround() != null) {
            iProblem.setPro4k2(entity.getWorkaround());
        }
        if (entity.getSolution() != null) {
            iProblem.setSolution(entity.getSolution());
        }
        if (entity.getCommentToInitiator() != null) {
            iProblem.setWorkaround(entity.getCommentToInitiator());
        }
        if (entity.getPriority() != null) {
            IImpact iImpact = iImpactDao.read(entity.getPriority().getId());
            iProblem.setImpact(iImpact);
        }
        if (entity.getDeadline() != null) {
            iProblem.setDeadline(DateUtils.toSDDate(entity.getDeadline()));
        }
        if (entity.getResolvedDate() != null) {
            iProblem.setActualFinish(DateUtils.toSDDate(entity.getResolvedDate()));
        }
        if (entity.getResolvedDate() != null) {
            iProblem.setLateFinish(DateUtils.toSDDate(entity.getClosureDate()));
        }
        if (entity.getOverdue() != null) {
            iProblem.setProBoolean12(entity.getOverdue());
        }
        if (entity.getWhoOverdue() != null) {
            iProblem.setProShorttext2(entity.getWhoOverdue());
        }
        if (entity.getPlanFinish() != null) {
            iProblem.setPlanFinish(DateUtils.toSDDate(entity.getPlanFinish()));
        }
        if (entity.getDeferralReason() != null) {
            iProblem.setProblemText2(entity.getDeferralReason());
        }
        if (entity.getAssignment() != null) {
            if (entity.getAssignment().getWorkgroup() != null) {
                IWorkgroup iWorkgroup = iWorkgroupDao.read(entity.getAssignment().getWorkgroup().getId());
                iProblem.getAssignment().setAssWorkgroup(iWorkgroup);
            }
            if (entity.getAssignment().getExecutor() != null) {
                IPerson executor = iPersonDao.read(entity.getAssignment().getExecutor().getId());
                iProblem.getAssignment().setAssigneePerson(executor);
            }
            iProblem.getAssignment().transfer();
        }
        if (entity.getCategory() != null) {
            IProblemCategory iProblemCategory = iProblemCategoryDao.read(entity.getCategory().getId());
            iProblem.setCategory(iProblemCategory);
        }
        if (entity.getClassification() != null) {
            IClassProblem iClassProblem = iClassProblemDao.read(entity.getClassification().getId());
            iProblem.setClassification(iClassProblem);
        }
        if (entity.getClosureCode() != null) {
            IProblemClosureCode iProblemClosureCode = iProblemClosureCodeDao.read(entity.getClosureCode().getId());
            iProblem.setClosureCode(iProblemClosureCode);
        }
        if (entity.getFolder() != null) {
            IFolder iFolder = iFolderDao.read(entity.getFolder().getId());
            iProblem.setFolder(iFolder);
        }
        if (entity.getNotAttachInReport() != null) {
            iProblem.setProBoolean1(entity.getNotAttachInReport());
        }
        if (entity.getVersionDate() != null) {
            iProblem.setProblemDate2(DateUtils.toSDDate(entity.getVersionDate()));
        }
        if (entity.getCommentToExecutor() != null) {
            iProblem.setPro4k1(entity.getCommentToExecutor());
        }
    }
}
