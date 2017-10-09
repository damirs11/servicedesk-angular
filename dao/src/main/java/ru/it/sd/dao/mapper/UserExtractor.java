package ru.it.sd.dao.mapper;

import ru.it.sd.dao.DBUtils;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.it.sd.model.Role;

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

	private final PersonDao personDao;
	private final UserMapper userMapper;
	private final RoleMapper roleMapper;

	@Autowired
	public UserExtractor(PersonDao personDao, UserMapper userMapper, RoleMapper roleMapper) {
		this.personDao = personDao;
		this.userMapper = userMapper;
		this.roleMapper = roleMapper;
	}

	@Override
	public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<Long, User> userCache = new HashMap<>();

		List<User> list = new ArrayList<>();
		while(rs.next()){
			Long userId = DBUtils.getLong(rs, "acc_oid");
			User user = userCache.get(userId);
			if(Objects.isNull(user)) {
				user = userMapper.mapRow(rs, 0);
				user.setPerson(personDao.readByUserId(userId));
				userCache.put(userId, user);
			}
			// Добавляем пользователю роль
			if (Objects.nonNull(DBUtils.getLong(rs, "rol_oid"))) {
				Role userRole = roleMapper.mapRow(rs, 0);
				user.getRoles().add(userRole);
			}
		}
		list.addAll(userCache.values());
		return list;
	}
}