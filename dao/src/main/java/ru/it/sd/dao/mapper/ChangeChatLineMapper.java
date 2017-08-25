package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.DBUtils;
import ru.it.sd.dao.UserDao;
import ru.it.sd.model.ChangeChatLine;
import ru.it.sd.model.ChangeHistoryLine;
import ru.it.sd.model.ChatLineType;
import ru.it.sd.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Маппер записей в истории
 */
@Component
public class ChangeChatLineMapper extends EntityRowMapper<ChangeChatLine> {

	@Autowired
	private UserDao userDao;

	@Override
	public ChangeChatLine mapRow(ResultSet rs, int rowNumber) throws SQLException {
		ChangeChatLine historyLine = super.mapRow(rs, rowNumber);

		Long accountId = DBUtils.getLong(rs,"reg_created_by_oid");
		if (Objects.nonNull(accountId)){
			User account = userDao.read(accountId);
			historyLine.setAccount(account);
		}

		Long typeId = DBUtils.getLong(rs,"hch_valueatr_oid");
		if (Objects.nonNull(typeId)){
			ChatLineType type = ChatLineType.get(typeId);
			historyLine.setType(type);
		}

		return historyLine;
	}
}
