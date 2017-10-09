package ru.it.sd.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.UserExtractor;
import ru.it.sd.exception.AppException;
import ru.it.sd.model.User;
import ru.it.sd.util.ResourceMessages;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

/**
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class UserDao extends AbstractDao {

	private static final String SELECT_ALL_SQL =
			"SELECT \n" +
			"a.acc_oid, a.acc_loginname, a.acc_showname, \n" +
			"rpa_acc_oid, rol_oid, rol_description, rol_updateallallowed \n" +
			"FROM \n" +
			"rep_accounts a\n" +
			"LEFT JOIN rep_roles_per_account rpa ON rpa.rpa_acc_oid = a.acc_oid \n" +
			"LEFT JOIN rep_roles r ON rpa_rol_oid = r.rol_oid \n{0}" ;

	private UserExtractor userExtractor;

	public UserDao(UserExtractor userExtractor) {
		this.userExtractor = userExtractor;
	}

	/**
	 * Находит пользователя по его логину
	 *
	 * @param login имя пользователя для входа в систему
	 * @return пользователь или null, если пользователь не найден
	 */
	public User readByLogin(String login) {
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

	/**
	 * Находит пользователя по его ID
	 *
	 * @param id идентификатор пользователя
	 * @return пользователь или null, если пользователь не найден
	 */
	public User read(Long id) {
		Objects.requireNonNull(id);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);

		List<User> users = namedJdbc.query(MessageFormat.format(SELECT_ALL_SQL, "\nWHERE acc_oid = :id"),
				params, userExtractor);
		if (users.isEmpty()) {
			return null;
		}
		if (users.size() > 1) {
			throw new AppException(ResourceMessages.getMessage("error.too.many.result"));
		}
		return users.get(0);
	}

	public User read(Integer id) {
		return read(Long.valueOf(id));
	}
}