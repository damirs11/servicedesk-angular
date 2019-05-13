package ru.it.sd.dao.mapper.problem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.UserDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.ChangeHistory;
import ru.it.sd.model.HistoryType;
import ru.it.sd.model.ProblemHistory;
import ru.it.sd.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Маппер записей в истории
 */
@Component
public class ProblemHistoryMapper extends EntityRowMapper<ProblemHistory> {

	@Autowired
	private UserDao userDao;

	@Override
	public ProblemHistory mapRow(ResultSet rs, int rowNumber) throws SQLException {
		ProblemHistory historyLine = super.mapRow(rs, rowNumber);

		Long accountId = DBUtils.getLong(rs,"reg_created_by_oid");
		if (Objects.nonNull(accountId)){
			User account = userDao.read(accountId);
			historyLine.setAccount(account);
		}

		Long fieldId = DBUtils.getLong(rs, "hpr_valueatr_oid");
		if ( Objects.nonNull(fieldId) ) {
			HistoryType type = HistoryType.getByField(fieldId);
			historyLine.setType(type);
		}
		return historyLine;
	}
}