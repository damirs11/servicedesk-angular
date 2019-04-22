package ru.it.sd.dao.mapper.workorder;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Маппер нарядов
 */
@Component
public class WorkorderListMapper extends EntityRowMapper<Workorder> {

    private final PersonDao personDao;
    private final CodeDao codeDao;
    private final AssignmentListMapper assignmentMapper;

    public WorkorderListMapper(PersonDao personDao,
                               CodeDao codeDao,
                               AssignmentListMapper assignmentMapper) {
        this.personDao = personDao;
        this.codeDao = codeDao;
        this.assignmentMapper = assignmentMapper;
    }

    @Override
    public List<Workorder> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Person> personCache = new HashMap<>();
        Map<Long, BaseCode> codeCache = new HashMap<>();
        List<Workorder> workorders = new ArrayList<>();
        while (rs.next()) {
            Workorder workorder = super.mapRow(rs, 0);
            Assignment assignment = assignmentMapper.mapRow(rs, 0);
            workorder.setAssignment(assignment);

            Long statusId = DBUtils.getLong(rs, "wor_sta_oid");
            if (statusId != null) {
                BaseCode code = getCode(codeCache, statusId);
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
                Person initiator = getPerson(personCache, initiatorId);
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
            workorders.add(workorder);
        }
        return workorders;
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
