package ru.it.sd.dao.mapper.servicecall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.ConfigurationItemDao;
import ru.it.sd.dao.OrganizationDao;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.dao.ServiceDao;
import ru.it.sd.dao.ServiceLevelAgreementDao;
import ru.it.sd.dao.TemplateDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.mapper.assignment.AssignmentMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ServiceCallMapper extends EntityRowMapper<ServiceCall> {

    private final AssignmentMapper assignmentMapper;
    private final CodeDao codeDao;
    private final TemplateDao templateDao;
    private final PersonDao personDao;
    private final OrganizationDao organizationDao;
    private final ConfigurationItemDao configurationItemDao;
    private final ServiceLevelAgreementDao serviceLevelAgreementDao;
    private final ServiceDao serviceDao;

    @Autowired
    public ServiceCallMapper(AssignmentMapper assignmentMapper,
                             CodeDao codeDao,
                             TemplateDao templateDao,
                             PersonDao personDao,
                             OrganizationDao organizationDao,
                             ConfigurationItemDao configurationItemDao,
                             ServiceLevelAgreementDao serviceLevelAgreementDao,
                             ServiceDao serviceDao) {
        this.assignmentMapper = assignmentMapper;
        this.codeDao = codeDao;
        this.templateDao = templateDao;
        this.personDao = personDao;
        this.organizationDao = organizationDao;
        this.configurationItemDao = configurationItemDao;
        this.serviceLevelAgreementDao = serviceLevelAgreementDao;
        this.serviceDao = serviceDao;
    }

    @Override
    public ServiceCall mapRow(ResultSet rs, int rowNum) throws SQLException {
        ServiceCall serviceCall = super.mapRow(rs, rowNum);
        Assignment assignment = assignmentMapper.mapRow(rs, 0);
        serviceCall.setAssignment(assignment);

        Long folderId = DBUtils.getLong(rs, "ser_poo_oid");
        if (folderId != null) {
            BaseCode code = codeDao.read(folderId);
            serviceCall.setFolder(code.convertTo(Folder.class));
        }
        Long statusId = DBUtils.getLong(rs, "ser_sta_oid");
        if (statusId != null) {
            BaseCode code = codeDao.read(statusId);
            serviceCall.setStatus(code.convertTo(EntityStatus.class));
        }
        Long categoryId = DBUtils.getLong(rs, "ser_cat_oid");
        if (categoryId != null) {
            BaseCode code = codeDao.read(categoryId);
            serviceCall.setCategory(code.convertTo(EntityCategory.class));
        }
        Long classificationId = DBUtils.getLong(rs, "ser_cla_oid");
        if (classificationId != null) {
            BaseCode code = codeDao.read(classificationId);
            serviceCall.setClassification(code.convertTo(EntityClassification.class));
        }
        Long closureCodeId = DBUtils.getLong(rs, "ser_clo_oid");
        if (closureCodeId != null) {
            BaseCode code = codeDao.read(closureCodeId);
            serviceCall.setClosureCode(code.convertTo(EntityClosureCode.class));
        }
        Long templateId = DBUtils.getLong(rs, "ser_tem_oid");
        if (templateId != null) {
            Template template = templateDao.read(templateId);
            serviceCall.setTemplate(template);
        }
        Long initiatorId = DBUtils.getLong(rs, "ser_requestor_per_oid");
        if (initiatorId != null) {
            Person person = personDao.read(initiatorId);
            serviceCall.setInitiator(person);
        }
        Long callerId = DBUtils.getLong(rs, "ser_caller_per");
        if (callerId != null) {
            Person person = personDao.read(callerId);
            serviceCall.setCaller(person);
        }
        Long organizationId = DBUtils.getLong(rs, "ser_caller_org");
        if (organizationId != null) {
            Organization organization = organizationDao.read(organizationId);
            serviceCall.setOrganization(organization);
        }
        Long configurationItemId = DBUtils.getLong(rs, "ser_cit_oid");
        if (configurationItemId != null) {
            ConfigurationItem configurationItem = configurationItemDao.read(configurationItemId);
            serviceCall.setConfigurationItem(configurationItem);
        }
        Long priorityId = DBUtils.getLong(rs, "ser_imp_oid");
        if (priorityId != null) {
            BaseCode code = codeDao.read(priorityId);
            serviceCall.setPriority(code.convertTo(EntityPriority.class));
        }
        Long serviceLevelPriority = DBUtils.getLong(rs, "ser_pri_oid");
        if (serviceLevelPriority != null) {
            BaseCode code = codeDao.read(serviceLevelPriority);
            serviceCall.setServiceLevelPriority(code.convertTo(ServiceLevelPriority.class));
        }
        Long executorHeadId = DBUtils.getLong(rs, "scf_per1_oid");
        if (executorHeadId != null) {
            Person person = personDao.read(executorHeadId);
            serviceCall.setExecutorHead(person);
        }
        Long markId = DBUtils.getLong(rs, "scf_cod1_oid");
        if (markId != null) {
            BaseCode code = codeDao.read(markId);
            serviceCall.setMark(code.convertTo(EntityCode1.class));
        }
        Long sla = DBUtils.getLong(rs, "ser_sla_oid");
        if (sla != null) {
            ServiceLevelAgreement serviceLevelAgreement = serviceLevelAgreementDao.read(sla);
            serviceCall.setServiceLevelAgreement(serviceLevelAgreement);
        }
        Long serviceId = DBUtils.getLong(rs, "ser_srv_oid");
        if (serviceId != null) {
            Service service = serviceDao.read(serviceId);
            serviceCall.setService(service);
        }
        Long renewalReasonId = DBUtils.getLong(rs, "scf_cod7_oid");
        if (renewalReasonId != null) {
            BaseCode code = codeDao.read(renewalReasonId);
            serviceCall.setRenewalReason(code.convertTo(EntityCode7.class));
        }
        Long sourceId = DBUtils.getLong(rs, "ser_med_oid");
        if (sourceId != null) {
            BaseCode code = codeDao.read(sourceId);
            serviceCall.setSource(code.convertTo(Source.class));
        }
        Long faqId = DBUtils.getLong(rs, "ser_faq_oid");
        if (faqId != null) {
            BaseCode code = codeDao.read(faqId);
            serviceCall.setFaq(code.convertTo(FAQ.class));
        }
        Long functionalId = DBUtils.getLong(rs, "scf_cod2_oid");
        if (functionalId != null) {
            BaseCode code = codeDao.read(functionalId);
            serviceCall.setFunctional(code.convertTo(Functional.class));
        }
        Long notificationId = DBUtils.getLong(rs, "scf_cod4_oid");
        if (notificationId != null) {
            BaseCode code = codeDao.read(notificationId);
            serviceCall.setNotification(code.convertTo(Notification.class));
        }
        Long responsibilityAreaId = DBUtils.getLong(rs, "scf_cod5_oid");
        if (responsibilityAreaId != null) {
            BaseCode code = codeDao.read(responsibilityAreaId);
            serviceCall.setResponsibilityArea(code.convertTo(ResponsibilityArea.class));
        }
        double duration = rs.getDouble("scf_duration1");
        if (duration != 0) {
            //Переводим продолжительность из double в миллисекунды
            serviceCall.setLaborCosts(Math.round(duration * 24 * 60 * 60 * 1000));
        }
        Long entityCode3Id = DBUtils.getLong(rs, "scf_cod3_oid");
        if (entityCode3Id != null) {
            BaseCode code = codeDao.read(entityCode3Id);
            serviceCall.setEntityCode3(code.convertTo(EntityCode3.class));
        }
        Long entityCode6Id = DBUtils.getLong(rs, "scf_cod6_oid");
        if (entityCode6Id != null) {
            BaseCode code = codeDao.read(entityCode6Id);
            serviceCall.setEntityCode6(code.convertTo(EntityCode6.class));
        }
        return serviceCall;
    }
}
