package ru.it.sd.dao.mapper;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.*;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер нарядов
 */
@Component
public class WorkorderMapper extends EntityRowMapper<Workorder> {

	private PersonDao personDao;
	private ChangeDao changeDao;
    private WorkgroupDao workgroupDao;
    private CodeDao codeDao;

    public WorkorderMapper(PersonDao personDao, ChangeDao changeDao, WorkgroupDao workgroupDao,
    CodeDao codeDao) {
	    this.personDao = personDao;
	    this.changeDao = changeDao;
	    this.workgroupDao = workgroupDao;
	    this.codeDao = codeDao;
    }

	@Override
	public Workorder mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Workorder workorder = super.mapRow(rs, rowNumber);

		Long statusId = DBUtils.getLong(rs,"wor_sta_oid");
		if (statusId != null){
			BaseCode code = codeDao.read(statusId);
			workorder.setStatus(code.convertTo(EntityStatus.class));
		}
		Long categoryId = DBUtils.getLong(rs,"wor_cat_oid");
		if (categoryId != null){
			EntityCategory category = EntityCategory.getById(categoryId);
			workorder.setCategory(category);
		}
		Long closureCodeId = DBUtils.getLong(rs,"wor_clo_oid");
		if (closureCodeId != null){
			EntityClosureCode closureCode = EntityClosureCode.getById(closureCodeId);
			workorder.setClosureCode(closureCode);
		}
		Long initiatorId = DBUtils.getLong(rs,"wor_requestor_per_oid");
		if (initiatorId != null){
			Person initiator = personDao.read(initiatorId);
			workorder.setInitiator(initiator);
		}
		Long assigneePersonId = DBUtils.getLong(rs,"ass_per_to_oid");
		if (assigneePersonId != null){
			Person assigneePerson = personDao.read(assigneePersonId);
			workorder.setAssigneePerson(assigneePerson);
		}
		Long changeId = DBUtils.getLong(rs,"wor_cha_oid");
		if (changeId != null){
			Change change = changeDao.read(changeId);
			workorder.setChange(change);
		}
        Long workgroupId = DBUtils.getLong(rs,"ass_workgroup");
        if (workgroupId != null){
            Workgroup workgroup = workgroupDao.read(workgroupId);
            workorder.setAssWorkgroup(workgroup);
        }

		return workorder;
	}
}
