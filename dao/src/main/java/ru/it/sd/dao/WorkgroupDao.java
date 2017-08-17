package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.WorkgroupMapper;
import ru.it.sd.model.Workgroup;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Дао для работы с данными нарядов
 */
@Repository
public class WorkgroupDao extends AbstractEntityDao<Workgroup> {

	@Autowired
	private WorkgroupMapper mapper;

	/**
	 * Общий запрос получения данных о группе
	 */
	private static final String BASE_SQL =
		"SELECT " +
			"wg.wog_oid, " +
			"wg.wog_name, " +
			"wg.wog_searchcode, " +
			"wg.wog_sta_oid, " +
			"wg.wog_parent " +
		"FROM " +
			"itsm_workgroups AS wg";

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<Workgroup> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (RowMapper<Workgroup>) mapper);
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		super.buildWhere(filter, sql, params);

		// Фильтр по родителю, то есть получаем все дочерние группы
		if (Objects.nonNull(filter) && filter.containsKey("parent")) {
			params.addValue("parent", filter.get("parent"));
			sql.append(" AND wog_parent = :parent");
		}

		if (Objects.nonNull(filter) && filter.containsKey("person")) {
			params.addValue("person", filter.get("person"));
			sql.append(" AND wg.wog_oid IN (" +
					"SELECT mem_wog_oid FROM itsm_members " +
					"WHERE mem_per_oid = :person)");
		}
	}
}