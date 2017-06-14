package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.ChangeDao;
import ru.it.sd.dao.DBUtils;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Маппер нарядов
 */
@Component
public class WorkorderMapper extends EntityRowMapper<Workorder> {

	@Autowired
	private PersonDao personDao;
	@Autowired
	private ChangeDao changeDao;

	@Override
	public Workorder mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Workorder workorder = super.mapRow(rs, rowNumber);

		Long statusId = DBUtils.getLong(rs,"WOR_STA_OID");
		if (statusId != null){
			EntityStatus status = EntityStatus.getById(statusId);
			workorder.setStatus(status);
		}
		Long categoryId = DBUtils.getLong(rs,"WOR_CAT_OID");
		if (categoryId != null){
			EntityCategory category = EntityCategory.getById(categoryId);
			workorder.setCategory(category);
		}
		Long closureCodeId = DBUtils.getLong(rs,"WOR_CLO_OID");
		if (closureCodeId != null){
			EntityClosureCode closureCode = EntityClosureCode.getById(closureCodeId);
			workorder.setClosureCode(closureCode);
		}
		Long initiatorId = DBUtils.getLong(rs,"WOR_REQUESTOR_PER_OID");
		if (initiatorId != null){
			Person initiator = personDao.findOne(initiatorId);
			workorder.setInitiator(initiator);
		}
		Long assigneePersonId = DBUtils.getLong(rs,"ASS_PER_TO_OID");
		if (assigneePersonId != null){
			Person assigneePerson = personDao.findOne(assigneePersonId);
			workorder.setAssigneePerson(assigneePerson);
		}
		Long changeId = DBUtils.getLong(rs,"WOR_CHA_OID");
		if (changeId != null){
			Change change = changeDao.findOne(changeId);
			workorder.setChange(change);
		}

		return workorder;
	}
}
