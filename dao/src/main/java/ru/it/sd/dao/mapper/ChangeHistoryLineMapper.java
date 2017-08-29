package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.ChangeHistoryLineDao;
import ru.it.sd.dao.DBUtils;
import ru.it.sd.dao.UserDao;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Маппер записей в истории
 */
@Component
public class ChangeHistoryLineMapper extends EntityRowMapper<ChangeHistoryLine> {

	@Autowired
	private UserDao userDao;

	@Override
	public ChangeHistoryLine mapRow(ResultSet rs, int rowNumber) throws SQLException {
		ChangeHistoryLine historyLine = super.mapRow(rs, rowNumber);

		Long accountId = DBUtils.getLong(rs,"reg_created_by_oid");
		if (Objects.nonNull(accountId)){
			User account = userDao.read(accountId);
			historyLine.setAccount(account);
		}

		Long fieldId = DBUtils.getLong(rs, "hch_valueatr_oid");
		if ( Objects.nonNull(fieldId) ) {
			HistoryLineType type = HistoryLineType.getByField(fieldId);
			historyLine.setType(type);
		}
		return historyLine;
	}
}
