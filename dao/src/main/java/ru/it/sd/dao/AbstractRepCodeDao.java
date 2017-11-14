package ru.it.sd.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import ru.it.sd.model.AbstractItsmCode;
import ru.it.sd.model.AbstractRepCode;
import ru.it.sd.model.SortingInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author quadrix
 * @since 15.11.2017
 */
public abstract class AbstractRepCodeDao<T extends AbstractRepCode> extends AbstractEntityDao<T> {

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(CodeDao.SELECT_REP_SQL);
	}

	@Override
	protected List<T> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, getMapper());
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		super.buildWhere(filter, sql, params);
		sql.append(" AND rcd.rcd_codedisabled = 0");
	}

	protected abstract RowMapper<T> getMapper();

	@Override
	protected List<String> getSortableColumnNames(SortingInfo sort) {
		return Arrays.asList(new String[]{"rcd_ordering"});
	}

}
