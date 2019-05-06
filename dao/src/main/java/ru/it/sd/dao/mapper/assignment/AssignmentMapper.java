package ru.it.sd.dao.mapper.assignment;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.Assignment;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.EntityPriority;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.Person;
import ru.it.sd.model.Workgroup;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер для "назначений"
 */
@Component
public class AssignmentMapper extends EntityRowMapper<Assignment> {

	private PersonDao personDao;
	private CodeDao codeDao;
	private WorkgroupDao workgroupDao;

    public AssignmentMapper(PersonDao personDao, CodeDao codeDao, WorkgroupDao workgroupDao) {
	    this.personDao = personDao;
	    this.codeDao = codeDao;
	    this.workgroupDao = workgroupDao;
    }

	@Override
	public Assignment mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Assignment assignment = super.mapRow(rs, rowNumber);

		Long statusId = DBUtils.getLong(rs,"ass_status");
		if (statusId != null){
			BaseCode code = codeDao.read(statusId);
			assignment.setStatus(code.convertTo(EntityStatus.class));
		}

		Long priorityId = DBUtils.getLong(rs,"ass_priority");
		if (priorityId != null){
			BaseCode code = codeDao.read(priorityId);
			assignment.setPriority(code.convertTo(EntityPriority.class));
		}

		Long executorId = DBUtils.getLong(rs,"ass_person_to");
		if (executorId  != null){
			Person person = personDao.read(executorId);
			assignment.setExecutor(person);
		}

		Long workgroupId = DBUtils.getLong(rs,"ass_workgroup_to");
		if (workgroupId  != null){
			Workgroup workgroup = workgroupDao.read(workgroupId);
			assignment.setWorkgroup(workgroup);
		}
		return assignment;
	}
}
