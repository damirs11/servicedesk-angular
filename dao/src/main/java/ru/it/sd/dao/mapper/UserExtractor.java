package ru.it.sd.dao.mapper;

import ru.it.sd.dao.DBUtils;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.model.Role;
import ru.it.sd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Маппер персон
 *
 * @author quadrix
 * @since 03.05.2017
 */
@Component
public class UserExtractor implements ResultSetExtractor<List<User>> {

	@Autowired
	private PersonDao personDao;
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<Long, User> userCache = new HashMap<>();

		List<User> list = new ArrayList<>();
		int i = 0;
		while(rs.next()){
			Long userId = DBUtils.getLong(rs, "acc_oid");
			User user = userCache.get(userId);
			if(Objects.isNull(user)) {
				user = userMapper.mapRow(rs, 0);
				user.setPerson(personDao.findByUserId(userId));
				userCache.put(userId, user);
			}
			// Добавляем пользователю роль
			if (Objects.nonNull(DBUtils.getLong(rs, "rpa_acc_oid"))) {
				Long roleId = DBUtils.getLong(rs, "rpa_rol_oid");
				if (Objects.nonNull(roleId)) {
					Role role = Role.getById(roleId);
					if (Objects.nonNull(role)) {
						user.getRoles().add(role);
					}
				}
			}
		}
		list.addAll(userCache.values());
		return list;
	}
}