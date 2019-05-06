package ru.it.sd.dao.mapper.workorder;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.ChangeDao;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.OrganizationDao;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.dao.ProblemDao;
import ru.it.sd.dao.ServiceCallDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.mapper.assignment.AssignmentMapper;
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
public class WorkorderMapper extends EntityRowMapper<Workorder> {

	private final PersonDao personDao;
	private final ChangeDao changeDao;
	private final ProblemDao problemDao;
    private final CodeDao codeDao;
    private final AssignmentMapper assignmentMapper;
    private final OrganizationDao organizationDao;
	private final ServiceCallDao serviceCallDao;
    public WorkorderMapper(PersonDao personDao,
						   ChangeDao changeDao,
						   ProblemDao problemDao,
						   CodeDao codeDao,
						   AssignmentMapper assignmentMapper,
						   OrganizationDao organizationDao,
						   ServiceCallDao serviceCallDao) {
	    this.personDao = personDao;
	    this.changeDao = changeDao;
	    this.problemDao = problemDao;
	    this.codeDao = codeDao;
	    this.assignmentMapper = assignmentMapper;
	    this.organizationDao = organizationDao;
		this.serviceCallDao = serviceCallDao;
	}

	@Override
	public Workorder mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Workorder workorder = super.mapRow(rs, rowNumber);
		Assignment assignment = assignmentMapper.mapRow(rs, rowNumber);
		workorder.setAssignment(assignment);

		Long statusId = DBUtils.getLong(rs,"wor_sta_oid");
		if (statusId != null){
			BaseCode code = codeDao.read(statusId);
			workorder.setStatus(code.convertTo(EntityStatus.class));
		}
		Long categoryId = DBUtils.getLong(rs,"wor_cat_oid");
		if (categoryId != null){
            BaseCode code = codeDao.read(categoryId);
			EntityCategory category = code.convertTo(EntityCategory.class);
			workorder.setCategory(category);
		}
		Long closureCodeId = DBUtils.getLong(rs,"wor_clo_oid");
		if (closureCodeId != null){
            BaseCode code = codeDao.read(closureCodeId);
			EntityClosureCode closureCode = code.convertTo(EntityClosureCode.class);
			workorder.setClosureCode(closureCode);
		}
		Long initiatorId = DBUtils.getLong(rs,"wor_requestor_per_oid");
		if (initiatorId != null){
			Person initiator = personDao.read(initiatorId);
			workorder.setInitiator(initiator);
		}
		Long changeId = DBUtils.getLong(rs,"wor_cha_oid");
		if (changeId != null){
			Change change = changeDao.read(changeId);
			workorder.setChange(change);
		}
		Long servicecallId = DBUtils.getLong(rs,"wor_ser_oid");
		if (servicecallId != null){
			ServiceCall serviceCall = serviceCallDao.read(servicecallId);
			workorder.setServicecall(serviceCall);
		}
		Long problemId = DBUtils.getLong(rs,"wor_pro_oid");
		if (problemId != null){
			Problem problem = problemDao.read(problemId);
			workorder.setProblem(problem);
		}
		Long folderId = DBUtils.getLong(rs, "wor_poo_oid");
		if(folderId != null){
			BaseCode code = codeDao.read(folderId);
			workorder.setFolder(code.convertTo(Folder.class));
		}

        Long orgId = DBUtils.getLong(rs, "wcf_org1_oid");
        if(orgId != null){
            Organization organization = organizationDao.read(orgId);
            workorder.setOrganization(organization);
        }

		return workorder;
	}
}
