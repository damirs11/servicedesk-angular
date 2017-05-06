package com.aplana.sd.dao;

import com.aplana.sd.dao.mapper.PersonMapper;
import com.aplana.sd.dao.mapper.UserExtractor;
import com.aplana.sd.exception.AppException;
import com.aplana.sd.model.Person;
import com.aplana.sd.model.User;
import com.aplana.sd.util.ResourceMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

/**
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class UserDao extends AbstractDao {

	@Autowired
	private UserExtractor userExtractor;

	private static final String SELECT_ALL_SQL =
			"SELECT \n" +
			"acc_oid, acc_loginname, acc_showname, \n" +
			"rpa_acc_oid, rpa_rol_oid \n" +
			"FROM \n" +
			"rep_accounts a\n" +
			"LEFT JOIN rep_roles_per_account rpa ON rpa.rpa_acc_oid = a.acc_oid {0}";

	/**
	 * Находит пользователя по его логину
	 *
	 * @param login
	 * @return пользователь или null, если пользователь не найден
	 */
	public User findByLogin(String login) {
		Objects.requireNonNull(login);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("login", login.toLowerCase());

		List<User> users = namedJdbc.query(MessageFormat.format(SELECT_ALL_SQL, "\nWHERE lower(acc_loginname) = :login"),
				params, userExtractor);
		if (users.isEmpty()) {
			return null;
		}
		if (users.size() > 1) {
			throw new AppException(ResourceMessages.getMessage("error.too.many.result"));
		}
		return users.get(0);
	}
}