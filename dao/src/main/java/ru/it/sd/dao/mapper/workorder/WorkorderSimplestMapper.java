package ru.it.sd.dao.mapper.workorder;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.mapper.assignment.AssignmentSimpleMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.Assignment;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.Change;
import ru.it.sd.model.EntityCategory;
import ru.it.sd.model.EntityClosureCode;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.Folder;
import ru.it.sd.model.Organization;
import ru.it.sd.model.Person;
import ru.it.sd.model.Problem;
import ru.it.sd.model.ServiceCall;
import ru.it.sd.model.Workorder;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер нарядов
 */
@Component
public class WorkorderSimplestMapper extends EntityRowMapper<Workorder> {

    private final AssignmentSimpleMapper assignmentMapper;

    public WorkorderSimplestMapper(AssignmentSimpleMapper assignmentMapper) {
        this.assignmentMapper = assignmentMapper;
    }

    @Override
    public Workorder mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Workorder workorder = super.mapRow(rs, rowNumber);
        Assignment assignment = assignmentMapper.mapRow(rs, rowNumber);
        workorder.setAssignment(assignment);

        Long statusId = DBUtils.getLong(rs, "wor_sta_oid");
        if (statusId != null) {
            BaseCode code = new BaseCode(statusId);
            workorder.setStatus(code.convertTo(EntityStatus.class));
        }
        Long categoryId = DBUtils.getLong(rs, "wor_cat_oid");
        if (categoryId != null) {
            BaseCode code = new BaseCode(categoryId);
            EntityCategory category = code.convertTo(EntityCategory.class);
            workorder.setCategory(category);
        }
        Long closureCodeId = DBUtils.getLong(rs, "wor_clo_oid");
        if (closureCodeId != null) {
            BaseCode code = new BaseCode(closureCodeId);
            EntityClosureCode closureCode = code.convertTo(EntityClosureCode.class);
            workorder.setClosureCode(closureCode);
        }
        Long initiatorId = DBUtils.getLong(rs, "wor_requestor_per_oid");
        if (initiatorId != null) {
            Person initiator = new Person(initiatorId);
            workorder.setInitiator(initiator);
        }
        Long changeId = DBUtils.getLong(rs, "wor_cha_oid");
        if (changeId != null) {
            workorder.setChange(new Change(changeId));
        }
        Long servicecallId = DBUtils.getLong(rs, "wor_ser_oid");
        if (servicecallId != null) {
            workorder.setServicecall(new ServiceCall(servicecallId));
        }
        Long problemId = DBUtils.getLong(rs, "wor_pro_oid");
        if (problemId != null) {
            workorder.setProblem(new Problem(problemId));
        }
        Long folderId = DBUtils.getLong(rs, "wor_poo_oid");
        if (folderId != null) {
            BaseCode code = new BaseCode(folderId);
            workorder.setFolder(code.convertTo(Folder.class));
        }

        Long orgId = DBUtils.getLong(rs, "wcf_org1_oid");
        if (orgId != null) {
            workorder.setOrganization(new Organization(orgId));
        }

        return workorder;
    }
}
