package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.dao.UserDao;
import ru.it.sd.model.HistoryLineType;
import ru.it.sd.model.User;
import ru.it.sd.model.WorkorderHistoryLine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Маппер записей в истории
 */
@Component
public class WorkorderHistoryLineMapper extends EntityRowMapper<WorkorderHistoryLine> {

	@Autowired
	private UserDao userDao;

	@Override
	public WorkorderHistoryLine mapRow(ResultSet rs, int rowNumber) throws SQLException {
		WorkorderHistoryLine historyLine = super.mapRow(rs, rowNumber);

		Long accountId = DBUtils.getLong(rs,"reg_created_by_oid");
		if (Objects.nonNull(accountId)){
			User account = userDao.read(accountId);
			historyLine.setAccount(account);
		}

		Long fieldId = DBUtils.getLong(rs, "hwk_valueatr_oid");
		if ( Objects.nonNull(fieldId) ) {
			HistoryLineType type = HistoryLineType.getByField(fieldId);
			historyLine.setType(type);
		}
		return historyLine;
	}
}
