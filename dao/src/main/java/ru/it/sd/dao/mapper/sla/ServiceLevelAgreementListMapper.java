package ru.it.sd.dao.mapper.sla;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.dao.ServiceDao;
import ru.it.sd.dao.ServiceLevelDao;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.DefaultPriority;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.Folder;
import ru.it.sd.model.Person;
import ru.it.sd.model.Service;
import ru.it.sd.model.ServiceLevel;
import ru.it.sd.model.ServiceLevelAgreement;
import ru.it.sd.model.Workgroup;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ServiceLevelAgreementListMapper extends EntityRowMapper<ServiceLevelAgreement> {

    private final CodeDao codeDao;
    private final ServiceDao serviceDao;
    private final ServiceLevelDao serviceLevelDao;
    private final PersonDao personDao;
    private final WorkgroupDao workgroupDao;

    @Autowired
    public ServiceLevelAgreementListMapper(CodeDao codeDao, ServiceDao serviceDao, ServiceLevelDao serviceLevelDao, WorkgroupDao workgroupDao, PersonDao personDao) {
        this.codeDao = codeDao;
        this.serviceDao = serviceDao;
        this.serviceLevelDao = serviceLevelDao;
        this.workgroupDao = workgroupDao;
        this.personDao = personDao;
    }

    @Override
    public ServiceLevelAgreement mapRow(ResultSet rs, int rowNum) throws SQLException {
        ServiceLevelAgreement serviceLevelAgreement = super.mapRow(rs, rowNum);
        if (serviceLevelAgreement == null) return null;
        Long folderId = DBUtils.getLong(rs, "sla_pool_cod_oid");
        if (folderId != null) {
            BaseCode code = new BaseCode(folderId);
            serviceLevelAgreement.setFolder(code.convertTo(Folder.class));
        }
        Long statusId = DBUtils.getLong(rs, "sla_status_cod_oid");
        if (statusId != null) {
            BaseCode code = codeDao.read(statusId, AbstractEntityDao.MapperMode.SIMPLEST);
            serviceLevelAgreement.setStatus(code.convertTo(EntityStatus.class));
        }
        Long serviceId = DBUtils.getLong(rs, "sla_srv_oid");
        if (serviceId != null) {
            Service service = serviceDao.read(serviceId, AbstractEntityDao.MapperMode.SIMPLEST);
            serviceLevelAgreement.setService(service);
        }
        Long serviceLevelId = DBUtils.getLong(rs, "sla_sel_oid");
        if (serviceLevelId != null) {
            ServiceLevel serviceLevel = serviceLevelDao.read(serviceLevelId, AbstractEntityDao.MapperMode.SIMPLEST);
            serviceLevelAgreement.setServiceLevel(serviceLevel);
        }
        Long personId = DBUtils.getLong(rs, "sla_per_oid");
        if (personId != null) {
            Person person = personDao.read(personId, AbstractEntityDao.MapperMode.SIMPLEST);
            serviceLevelAgreement.setPerson(person);
        }
        Long workgroupId = DBUtils.getLong(rs, "sla_wog_oid");
        if (workgroupId != null) {
            Workgroup workgroup = workgroupDao.read(workgroupId, AbstractEntityDao.MapperMode.SIMPLEST);
            serviceLevelAgreement.setWorkgroup(workgroup);
        }
        Long defaultPriorityId = DBUtils.getLong(rs, "slc_cod1_oid");
        if (defaultPriorityId != null) {
            BaseCode defaultPriority = codeDao.read(defaultPriorityId);
            serviceLevelAgreement.setDefaultPriority(defaultPriority.convertTo(DefaultPriority.class));
        }
        return serviceLevelAgreement;
    }
}
