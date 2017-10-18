package ru.it.sd.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.RoleMapper;
import ru.it.sd.model.Role;

import java.util.List;

/**
 * Дао для работы с ролями
 *
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class RoleDao extends AbstractEntityDao<Role> {

	private static final String BASE_SQL =
			" SELECT\n" +
			"   rol_oid, " +
			"   rol_description, " +
			"   rol_updateallallowed " +
			" FROM\n" +
			"   rep_roles";

	private RowMapper<Role> mapper;

	public RoleDao(RoleMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<Role> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, mapper);
	}
}