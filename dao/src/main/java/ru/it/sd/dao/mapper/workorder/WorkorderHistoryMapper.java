package ru.it.sd.dao.mapper.workorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.dao.UserDao;
import ru.it.sd.model.HistoryType;
import ru.it.sd.model.User;
import ru.it.sd.model.WorkorderHistory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Маппер записей в истории
 */
@Component
public class WorkorderHistoryMapper extends EntityRowMapper<WorkorderHistory> {

	@Autowired
	private UserDao userDao;

	@Override
	public WorkorderHistory mapRow(ResultSet rs, int rowNumber) throws SQLException {
		WorkorderHistory historyLine = super.mapRow(rs, rowNumber);

		Long accountId = DBUtils.getLong(rs,"reg_created_by_oid");
		if (Objects.nonNull(accountId)){
			User account = userDao.read(accountId);
			historyLine.setAccount(account);
		}

		Long fieldId = DBUtils.getLong(rs, "hwk_valueatr_oid");
		if ( Objects.nonNull(fieldId) ) {
			HistoryType type = HistoryType.getByField(fieldId);
			historyLine.setType(type);
		}
		return historyLine;
	}
}
