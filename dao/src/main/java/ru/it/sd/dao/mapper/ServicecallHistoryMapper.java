package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.UserDao;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.HistoryType;
import ru.it.sd.model.ServicecallHistory;
import ru.it.sd.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Маппер записей в истории
 */
@Component
public class ServicecallHistoryMapper extends EntityRowMapper<ServicecallHistory> {

	@Autowired
	private UserDao userDao;

	@Override
	public ServicecallHistory mapRow(ResultSet rs, int rowNumber) throws SQLException {
		ServicecallHistory historyLine = super.mapRow(rs, rowNumber);
		Long accountId = DBUtils.getLong(rs,"reg_createdby_oid");
		if (Objects.nonNull(accountId)){
			User account = userDao.read(accountId);
			historyLine.setAccount(account);
		}

		Long fieldId = DBUtils.getLong(rs, "hsc_valueatr_oid");
		if ( Objects.nonNull(fieldId) ) {
			HistoryType type = HistoryType.getByField(fieldId);
			historyLine.setType(type);
		}
		return historyLine;
	}
}
