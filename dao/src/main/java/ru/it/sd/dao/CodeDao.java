package ru.it.sd.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.CodeMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.BaseCode;
import ru.it.sd.util.ResourceMessages;

import java.util.List;
import java.util.Map;

import static ru.it.sd.model.SortingInfo.SORTING_PARAM_NAME;

/**
 * Интерфейс для поиска кодов.
 */
@Repository
public class CodeDao extends AbstractEntityDao<BaseCode>{

	private CodeMapper mapper;
	private DBUtils dbUtils;

	public CodeDao(CodeMapper mapper, DBUtils dbUtils) {
		this.mapper = mapper;
		this.dbUtils = dbUtils;
	}

	String CHILDS_SQL =
		"with %s folder_rep(id) as(\n" +
		"	SELECT rcd.rcd_oid FROM rep_codes rcd WHERE rcd.rcd_rcd_oid = :parentId\n" +
		"	UNION ALL\n" +
		"	SELECT rcd.rcd_oid FROM rep_codes rcd\n" +
		"	INNER JOIN folder_rep ON folder_rep.id = rcd.rcd_rcd_oid\n" +
		"),\n"+
		"folder_cod(id) as(\n" +
		"	SELECT cod.COD_OID FROM ITSM_CODES cod WHERE cod.COD_cod_OID = :parentId\n" +
		"	UNION ALL\n" +
		"	SELECT cod.COD_OID FROM ITSM_CODES cod\n" +
		"	INNER JOIN folder_cod ON folder_cod.id = cod.COD_cod_OID\n" +
		")\n";

	String BASE_SQL =
		"SELECT id, name, ordering FROM\n" +
		"(SELECT\n" +
		"	cod.cod_oid id,\n" +
		"	cdl.cdl_name name,\n" +
		"	cod.cod_subtype subtype,\n" +
		"	cod.cod_ordering ordering\n" +
		"FROM\n" +
		"	itsm_codes cod\n" +
		"	LEFT JOIN itsm_codes_locale cdl ON (cdl.cdl_cod_oid = cod.cod_oid AND cdl.cdl_lng_oid = 1049)\n" +
		"WHERE cod.cod_disabled = 0\n" +
		"UNION ALL\n" +
		"SELECT\n" +
		"	rcd.rcd_oid id,\n" +
		"	rct.rct_name name,\n" +
		"	rcd.rcd_subtype subtype,\n" +
		"	rcd.rcd_ordering ordering\n" +
		"FROM\n" +
		"	rep_codes rcd\n" +
		"	LEFT JOIN rep_codes_text rct ON (rct.rct_rcd_oid = rcd.rcd_oid AND rct.rct_lng_oid = 1049)\n" +
		"WHERE rcd.rcd_codedisabled = 0) code\n";

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<BaseCode> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, mapper.asRowMapper());
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		if (filter == null || filter.isEmpty() ||
				!(filter.containsKey("id") || filter.containsKey("subtype")|| filter.containsKey("parentId"))) {
			throw new ServiceException(ResourceMessages.getMessage("error.dao.filter"));
		}
		super.buildWhere(filter, sql, params);
		if (filter.containsKey("subtype")) {
			params.addValue("subtype", filter.get("subtype"));
			sql.append(" AND subtype = :subtype");
		}

		if (filter.containsKey("parentId")) {
			sql.insert(0, String.format(CHILDS_SQL, dbUtils.isTest() ? "recursive" : "", dbUtils.isTest() ? "recursive" : ""));
			params.addValue("parentId", filter.get("parentId"));
			sql.append(" AND code.id in (SELECT folder_rep.id FROM folder_rep UNION SELECT folder_cod.id FROM folder_cod)");
		}
	}

	@Override
	protected void buildOrderBy(Map<String, String> filter, StringBuilder sql, int insertPos) {
		if (!filter.containsKey(SORTING_PARAM_NAME)) {
			filter.put(SORTING_PARAM_NAME, "ordering-asc");
		}
		super.buildOrderBy(filter, sql, insertPos);
	}
}