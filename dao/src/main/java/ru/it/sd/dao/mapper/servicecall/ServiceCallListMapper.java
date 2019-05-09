package ru.it.sd.dao.mapper.servicecall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.mapper.assignment.AssignmentListMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.Assignment;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.ConfigurationItem;
import ru.it.sd.model.EntityCategory;
import ru.it.sd.model.EntityClassification;
import ru.it.sd.model.EntityClosureCode;
import ru.it.sd.model.EntityCode1;
import ru.it.sd.model.EntityCode7;
import ru.it.sd.model.EntityPriority;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.Folder;
import ru.it.sd.model.Organization;
import ru.it.sd.model.Person;
import ru.it.sd.model.Service;
import ru.it.sd.model.ServiceCall;
import ru.it.sd.model.ServiceLevelAgreement;
import ru.it.sd.model.ServiceLevelPriority;
import ru.it.sd.model.Source;
import ru.it.sd.model.Template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ServiceCallListMapper extends EntityRowMapper<ServiceCall> {

    private final AssignmentListMapper assignmentMapper;
    private final CodeDao codeDao;
    private final PersonDao personDao;

    @Autowired
    public ServiceCallListMapper(AssignmentListMapper assignmentMapper,
                                 CodeDao codeDao,
                                 PersonDao personDao) {
        this.assignmentMapper = assignmentMapper;
        this.codeDao = codeDao;
        this.personDao = personDao;
    }

    @Override
    public List<ServiceCall> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Person> personCache = new HashMap<>();
        Map<Long, BaseCode> codeCache = new HashMap<>();
        List<ServiceCall> serviceCalls = new ArrayList<>();
        while (rs.next()) {
            ServiceCall serviceCall = super.mapRow(rs, 0);
            Assignment assignment = assignmentMapper.mapRow(rs, 0);
            serviceCall.setAssignment(assignment);

            Long folderId = DBUtils.getLong(rs, "ser_poo_oid");
            if (folderId != null) {
                BaseCode code = new BaseCode(folderId);
                serviceCall.setFolder(code.convertTo(Folder.class));
            }
            Long statusId = DBUtils.getLong(rs, "ser_sta_oid");
            if (statusId != null) {
                BaseCode code = getCode(codeCache, statusId);
                serviceCall.setStatus(code.convertTo(EntityStatus.class));
            }
            Long categoryId = DBUtils.getLong(rs, "ser_cat_oid");
            if (categoryId != null) {
                BaseCode code = new BaseCode(categoryId);
                serviceCall.setCategory(code.convertTo(EntityCategory.class));
            }
            Long classificationId = DBUtils.getLong(rs, "ser_cla_oid");
            if (classificationId != null) {
                BaseCode code = new BaseCode(classificationId);
                serviceCall.setClassification(code.convertTo(EntityClassification.class));
            }
            Long closureCodeId = DBUtils.getLong(rs, "ser_clo_oid");
            if (closureCodeId != null) {
                BaseCode code = new BaseCode(closureCodeId);
                serviceCall.setClosureCode(code.convertTo(EntityClosureCode.class));
            }
            Long templateId = DBUtils.getLong(rs, "ser_tem_oid");
            if (templateId != null) {
                Template template = new Template(templateId);
                serviceCall.setTemplate(template);
            }
            Long initiatorId = DBUtils.getLong(rs, "ser_requestor_per_oid");
            if (initiatorId != null) {
                Person person = getPerson(personCache, initiatorId);
                serviceCall.setInitiator(person);
            }
            Long callerId = DBUtils.getLong(rs, "ser_caller_per");
            if (callerId != null) {
                Person person = getPerson(personCache, callerId);
                serviceCall.setCaller(person);
            }
            Long organizationId = DBUtils.getLong(rs, "ser_caller_org");
            if (organizationId != null) {
                Organization organization = new Organization(organizationId);
                serviceCall.setOrganization(organization);
            }
            Long configurationItemId = DBUtils.getLong(rs, "ser_cit_oid");
            if (configurationItemId != null) {
                ConfigurationItem configurationItem = new ConfigurationItem(configurationItemId);
                serviceCall.setConfigurationItem(configurationItem);
            }
            Long priorityId = DBUtils.getLong(rs, "ser_imp_oid");
            if (priorityId != null) {
                BaseCode code = codeDao.read(priorityId, AbstractEntityDao.MapperMode.SIMPLEST);
                serviceCall.setPriority(code.convertTo(EntityPriority.class));
            }
            Long serviceLevelPriority = DBUtils.getLong(rs, "ser_pri_oid");
            if (serviceLevelPriority != null) {
                BaseCode code = new BaseCode(serviceLevelPriority);
                serviceCall.setServiceLevelPriority(code.convertTo(ServiceLevelPriority.class));
            }
            Long executorHeadId = DBUtils.getLong(rs, "scf_per1_oid");
            if (executorHeadId != null) {
                Person person = new Person(executorHeadId);
                serviceCall.setExecutorHead(person);
            }
            Long markId = DBUtils.getLong(rs, "scf_cod1_oid");
            if (markId != null) {
                BaseCode code = new BaseCode(markId);
                serviceCall.setMark(code.convertTo(EntityCode1.class));
            }
            Long sla = DBUtils.getLong(rs, "ser_sla_oid");
            if (sla != null) {
                ServiceLevelAgreement serviceLevelAgreement = new ServiceLevelAgreement(sla);
                serviceCall.setServiceLevelAgreement(serviceLevelAgreement);
            }
            Long serviceId = DBUtils.getLong(rs, "ser_srv_oid");
            if (serviceId != null) {
                Service service = new Service(serviceId);
                serviceCall.setService(service);
            }
            Long renewalReasonId = DBUtils.getLong(rs, "scf_cod7_oid");
            if (renewalReasonId != null) {
                BaseCode code = new BaseCode(renewalReasonId);
                serviceCall.setRenewalReason(code.convertTo(EntityCode7.class));
            }
            Long sourceId = DBUtils.getLong(rs, "ser_med_oid");
            if (sourceId != null) {
                BaseCode code = new BaseCode(sourceId);
                serviceCall.setSource(code.convertTo(Source.class));
            }
            serviceCalls.add(serviceCall);
        }
        return serviceCalls;
    }

    private Person getPerson(Map<Long, Person> cache, Long id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        Person person = personDao.read(id, AbstractEntityDao.MapperMode.SIMPLEST);
        cache.put(id, person);
        return person;
    }

    private BaseCode getCode(Map<Long, BaseCode> cache, Long id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        BaseCode code = codeDao.read(id, AbstractEntityDao.MapperMode.SIMPLEST);
        cache.put(id, code);
        return code;
    }

}
