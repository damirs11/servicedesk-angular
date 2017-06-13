package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.DBUtils;
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
	private PersonMapper personMapper;
	@Autowired
	private ChangeMapper changeMapper;

	@Override
	public Workorder mapCurrentRow(ResultSet rs, String prefix) throws SQLException {
		Workorder workorder = super.mapCurrentRow(rs, prefix);

		Long statusId = DBUtils.getLong(rs,getColumnName(prefix,"status.id"));
		if (statusId != null){
			EntityStatus status = EntityStatus.getById(statusId);
			workorder.setStatus(status);
		}
		Long categoryId = DBUtils.getLong(rs,getColumnName(prefix,"category.id"));
		if (categoryId != null){
			EntityCategory category = EntityCategory.getById(categoryId);
			workorder.setCategory(category);
		}
		Long closureCodeId = DBUtils.getLong(rs,getColumnName(prefix,"closureCode.id"));
		if (closureCodeId != null){
			EntityClosureCode closureCode = EntityClosureCode.getById(closureCodeId);
			workorder.setClosureCode(closureCode);
		}
		Long initiatorId = DBUtils.getLong(rs,getColumnName(prefix,"initiator.id"));
		if (initiatorId != null){
			String colName = getColumnName(prefix,getColumnName(prefix,"initiator"));
			Person initiator = personMapper.mapCurrentRow(rs,colName);
			workorder.setInitiator(initiator);
		}
		Long assigneePersonId = DBUtils.getLong(rs,getColumnName(prefix,"assigneePerson.id"));
		if (assigneePersonId != null){
			String colName = getColumnName(prefix,"assigneePerson");
			Person assigneePerson = personMapper.mapCurrentRow(rs,colName);
			workorder.setAssigneePerson(assigneePerson);
		}
		Long changeId = DBUtils.getLong(rs,getColumnName(prefix,"change.id"));
		if (changeId != null){
			String colName = getColumnName(prefix,"change");
			Change change = changeMapper.mapCurrentRow(rs,colName);
			workorder.setChange(change);
		}

		return workorder;
	}
}
