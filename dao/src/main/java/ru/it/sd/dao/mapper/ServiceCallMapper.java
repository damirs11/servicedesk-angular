package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.ConfigurationItemDao;
import ru.it.sd.dao.OrganizationDao;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.dao.TemplateDao;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.Assignment;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.ConfigurationItem;
import ru.it.sd.model.EntityCategory;
import ru.it.sd.model.EntityClassification;
import ru.it.sd.model.EntityClosureCode;
import ru.it.sd.model.EntityCode1;
import ru.it.sd.model.EntityPriority;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.Folder;
import ru.it.sd.model.Organization;
import ru.it.sd.model.Person;
import ru.it.sd.model.ServiceCall;
import ru.it.sd.model.Template;

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
    @Autowired
    public ServiceCallMapper(AssignmentMapper assignmentMapper, CodeDao codeDao, TemplateDao templateDao, PersonDao personDao, OrganizationDao organizationDao, ConfigurationItemDao configurationItemDao) {
        this.assignmentMapper = assignmentMapper;
        this.codeDao = codeDao;
        this.templateDao = templateDao;
        this.personDao = personDao;
        this.organizationDao = organizationDao;
        this.configurationItemDao = configurationItemDao;
    }

    @Override
    public ServiceCall mapRow(ResultSet rs, int rowNum) throws SQLException {
        ServiceCall serviceCall = super.mapRow(rs, rowNum);
        Assignment assignment = assignmentMapper.mapRow(rs, 0);
        serviceCall.setAssignment(assignment);

        Long folderId = DBUtils.getLong(rs, "ser_poo_oid");
        if(folderId != null){
            BaseCode code = codeDao.read(folderId);
            serviceCall.setFolder(code.convertTo(Folder.class));
        }
        Long statusId = DBUtils.getLong(rs, "ser_sta_oid");
        if(statusId != null){
            BaseCode code = codeDao.read(statusId);
            serviceCall.setStatus(code.convertTo(EntityStatus.class));
        }
        Long categoryId = DBUtils.getLong(rs, "ser_cat_oid");
        if(categoryId != null){
            BaseCode code = codeDao.read(categoryId);
            serviceCall.setCategory(code.convertTo(EntityCategory.class));
        }
        Long classificationId = DBUtils.getLong(rs, "ser_cla_oid");
        if(classificationId != null){
            BaseCode code = codeDao.read(classificationId);
            serviceCall.setClassification(code.convertTo(EntityClassification.class));
        }
        Long closureCodeId = DBUtils.getLong(rs, "ser_clo_oid");
        if(closureCodeId != null){
            BaseCode code = codeDao.read(closureCodeId);
            serviceCall.setClosureCode(code.convertTo(EntityClosureCode.class));
        }
        Long templateId = DBUtils.getLong(rs, "ser_tem_oid");
        if(templateId != null){
            Template template = templateDao.read(templateId);
            serviceCall.setTemplate(template);
        }
        Long initiatorId = DBUtils.getLong(rs, "ser_requestor_per_oid");
        if(initiatorId != null){
            Person person = personDao.read(initiatorId);
            serviceCall.setInitiator(person);
        }
        Long callerId = DBUtils.getLong(rs, "ser_caller_per");
        if(callerId != null){
            Person person = personDao.read(callerId);
            serviceCall.setCaller(person);
        }
        Long organizationId = DBUtils.getLong(rs, "ser_caller_org");
        if(organizationId != null){
            Organization organization = organizationDao.read(organizationId);
            serviceCall.setOrganization(organization);
        }
        Long configurationItemId = DBUtils.getLong(rs, "sci_cit_oid");
        if(configurationItemId != null){
            ConfigurationItem configurationItem = configurationItemDao.read(configurationItemId);
            serviceCall.setConfigurationItem(configurationItem);
        }
        Long priorityId = DBUtils.getLong(rs, "ser_imp_oid");
        if(priorityId != null){
            BaseCode code = codeDao.read(priorityId);
            serviceCall.setPriority(code.convertTo(EntityPriority.class));
        }
        Long executorHeadId = DBUtils.getLong(rs, "scf_per1_oid");
        if(executorHeadId != null){
            Person person = personDao.read(executorHeadId);
            serviceCall.setExecutorHead(person);
        }
        Long markId = DBUtils.getLong(rs, "scf_cod1_oid");
        if(markId != null){
            BaseCode code = codeDao.read(markId);
            serviceCall.setMark(code.convertTo(EntityCode1.class));
        }
        return serviceCall;
    }
}
