package ru.it.sd.dao.mapper.problem;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.mapper.assignment.AssignmentSimpleMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.Assignment;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.ConfigurationItem;
import ru.it.sd.model.EntityCategory;
import ru.it.sd.model.EntityClosureCode;
import ru.it.sd.model.EntityPriority;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.Folder;
import ru.it.sd.model.Person;
import ru.it.sd.model.Problem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер "Проблем"
 */
@Component
public class ProblemSimpleMapper extends EntityRowMapper<Problem> {

    private final AssignmentSimpleMapper assignmentMapper;

    public ProblemSimpleMapper(AssignmentSimpleMapper assignmentMapper) {
        this.assignmentMapper = assignmentMapper;
    }

    @Override
    public Problem mapRow(ResultSet rs, int rowNum) throws SQLException {
        Problem problem = super.mapRow(rs, rowNum);
        if (problem == null) return null;
        Assignment assignment = assignmentMapper.mapRow(rs, rowNum);
        if (assignment != null) {
            problem.setAssignment(assignment);
        }
        Long statusId = DBUtils.getLong(rs, "pro_sta_oid");
        if (statusId != null) {
            BaseCode code = new BaseCode(statusId);
            problem.setStatus(code.convertTo(EntityStatus.class));
        }
        Long initiatorId = DBUtils.getLong(rs, "pro_requestor_per_oid");
        if (initiatorId != null) {
            Person initiator = new Person(initiatorId);
            problem.setInitiator(initiator);
        }
        Long configurationItemId = DBUtils.getLong(rs, "pro_cit_oid");
        if (configurationItemId != null) {
            ConfigurationItem configurationItem = new ConfigurationItem(configurationItemId);
            problem.setConfigurationItem(configurationItem);
        }
        Long priorityId = DBUtils.getLong(rs, "pro_imp_oid");
        if (priorityId != null) {
            BaseCode code = new BaseCode(priorityId);
            EntityPriority priority = code.convertTo(EntityPriority.class);
            problem.setPriority(priority);
        }
        Long categoryId = DBUtils.getLong(rs, "pro_cat_oid");
        if (categoryId != null) {
            BaseCode code = new BaseCode(categoryId);
            EntityCategory category = code.convertTo(EntityCategory.class);
            problem.setCategory(category);
        }
        Long closureCodeId = DBUtils.getLong(rs, "pro_cla_oid");
        if (closureCodeId != null) {
            BaseCode code = new BaseCode(closureCodeId);
            EntityClosureCode closureCode = code.convertTo(EntityClosureCode.class);
            problem.setClosureCode(closureCode);
        }
        Long folderId = DBUtils.getLong(rs, "pro_poo_oid");
        if (folderId != null) {
            BaseCode code = new BaseCode(folderId);
            problem.setFolder(code.convertTo(Folder.class));
        }
        return problem;
    }
}