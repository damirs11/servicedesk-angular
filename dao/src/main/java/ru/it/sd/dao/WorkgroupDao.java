package ru.it.sd.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.mapper.workgroup.WorkgroupListMapper;
import ru.it.sd.dao.mapper.workgroup.WorkgroupMapper;
import ru.it.sd.dao.mapper.workgroup.WorkgroupSimpleMapper;
import ru.it.sd.model.Workgroup;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Дао для работы с данными нарядов
 */
@Repository
public class WorkgroupDao extends AbstractEntityDao<Workgroup> {

	private final WorkgroupMapper mapper;
	private final WorkgroupListMapper workgroupListMapper;
	private final WorkgroupSimpleMapper workgroupSimpleMapper;

	/**
	 * Общий запрос получения данных о группе
	 */
	private static final String BASE_SQL =
		"SELECT " +
			"wg.wog_oid, " +
			"wg.wog_name, " +
			"wg.wog_searchcode, " +
			"wg.wog_sta_oid, " +
			"wg.wog_parent, " +
			"wg.wog_poo_oid, " +
			"wgc.wgc_per1_oid " +
		"FROM " +
			"itsm_workgroups AS wg " +
		"LEFT JOIN itsm_wog_custom_fields wgc ON wgc.wgc_wog_oid = wg.wog_oid\n";

	public WorkgroupDao(WorkgroupMapper mapper, WorkgroupListMapper workgroupListMapper, WorkgroupSimpleMapper workgroupSimpleMapper) {
		this.mapper = mapper;
		this.workgroupListMapper = workgroupListMapper;
		this.workgroupSimpleMapper = workgroupSimpleMapper;
	}

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<Workgroup> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (RowMapper<Workgroup>) mapper);
	}

	@Override
	protected List<Workgroup> executeQuery(String sql, SqlParameterSource params, MapperMode mode) {
		EntityRowMapper<Workgroup> entityRowMapper;
		switch (mode) {
			case SIMPLEST:
				entityRowMapper = workgroupSimpleMapper;
				break;
			case LIST:
				entityRowMapper = workgroupListMapper;
				break;
			case FULL:
			default:
				entityRowMapper = mapper;
				break;
		}
		return namedJdbc.query(sql, params, entityRowMapper.asRowMapper());
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		super.buildWhere(filter, sql, params);

		if (Objects.nonNull(filter) && filter.containsKey("selectable")) {
			params.addValue("selectable", filter.get("selectable").equals("0") ? 1 : 0);
			sql.append(" AND wog_notselectable = :selectable");
		}
		// Фильтр по родителю, то есть получаем все дочерние группы
		if (Objects.nonNull(filter) && filter.containsKey("parentId")) {
			params.addValue("parentId", filter.get("parentId"));
			sql.append(" AND wog_parent = :parentId");
		}

		if (Objects.nonNull(filter) && filter.containsKey("personId")) {
			params.addValue("personId", filter.get("personId"));
			sql.append(" AND wg.wog_oid IN (" +
					"SELECT mem_wog_oid FROM itsm_members " +
					"WHERE mem_per_oid = :personId)");
		}

		if (Objects.nonNull(filter) && filter.containsKey("workgroupId")) {
			params.addValue("workgroupId", filter.get("workgroupId"));
			if (filter.containsKey("child")) {
				params.addValue("forward", 0);
			} else {
				params.addValue("forward", 1);
			}
			sql.append(" AND wg.wog_oid in (SELECT id FROM SdGetWorkGroups(:workgroupId, :forward))");
		}
	}
}