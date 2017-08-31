package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.ChangeDao;
import ru.it.sd.dao.DBUtils;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер нарядов
 */
@Component
public class WorkorderMapper extends EntityRowMapper<Workorder> {

	@Autowired
	private PersonDao personDao;
	@Autowired
	private ChangeDao changeDao;
    @Autowired
    private WorkgroupDao workgroupDao;

	@Override
	public Workorder mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Workorder workorder = super.mapRow(rs, rowNumber);

		Long statusId = DBUtils.getLong(rs,"wor_sta_oid");
		if (statusId != null){
			EntityStatus status = EntityStatus.get(statusId);
			workorder.setStatus(status);
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
        /*Long workgroupId = DBUtils.getLong(rs,"ass_workgroup");
        if (workgroupId != null){
            Workgroup workgroup = workgroupDao.read(workgroupId);
            workorder.setWorkgroup(workgroup);
        }*/

		return workorder;
	}
}
