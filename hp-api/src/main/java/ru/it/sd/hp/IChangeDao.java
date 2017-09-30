package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.*;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import ru.it.sd.dao.ChangeDao;
import ru.it.sd.hp.Utils.DateUtils;
import ru.it.sd.model.Change;
import org.springframework.stereotype.Repository;

@Repository
public class IChangeDao implements HpCrudDao<Change, IChange>{
    @Autowired
    private HpApi api;
    @Autowired
    private ChangeDao changeDao;
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
        IWorkgroup workgroup = iWorkgroupDao.read(entity.getAssWorkgroup().getId());
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
        Long no = changeDao.read(id).getNo();
        return api.getSdClient().sd_session().getChangeHome().openChange(no);
    }

    @Override
    public void update(Change entity) {
        SdClientBean sdClientBean = api.getSdClient();
        IChange iChange = read(entity.getId());

        IChangeCategory iChangeCategory = iChangeCategoryDao.read(entity.getCategory().getId());
        IClassificationCha iClassificationCha = iClassificationChaDao.read(entity.getClassification().getId());
        IPerson initiator = iPersonDao.read(entity.getInitiator().getId());
        IPerson manager = iPersonDao.read(entity.getManager().getId());
        IPerson executor = iPersonDao.read(entity.getExecutor().getId());
        IWorkgroup workgroup = iWorkgroupDao.read(entity.getAssWorkgroup().getId());
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

    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}
