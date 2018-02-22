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

	String BASE_SQL =
		"SELECT id, name, ordering FROM\n" +
		"(SELECT\n" +
		"	cod.cod_oid id,\n" +
		"	cdl.cdl_name name,\n" +
		"	cod.cod_subtype subtype,\n" +
		"	cod.cod_disabled disabled,\n" +
		"	cod.cod_ordering ordering\n" +
		"FROM\n" +
		"	itsm_codes cod\n" +
		"	LEFT JOIN itsm_codes_locale cdl ON (cdl.cdl_cod_oid = cod.cod_oid AND cdl.cdl_lng_oid = 1049)\n" +
		"UNION ALL\n" +
		"SELECT\n" +
		"	rcd.rcd_oid id,\n" +
		"	rct.rct_name name,\n" +
		"	rcd.rcd_subtype subtype,\n" +
		"	rcd.rcd_codedisabled disabled,\n" +
		"	rcd.rcd_ordering ordering\n" +
		"FROM\n" +
		"	rep_codes rcd\n" +
		"	LEFT JOIN rep_codes_text rct ON (rct.rct_rcd_oid = rcd.rcd_oid AND rct.rct_lng_oid = 1049)\n" +
		") code\n";

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
				!(filter.containsKey("id") || filter.containsKey("subtype")|| filter.containsKey("codeId"))) {
			throw new ServiceException(ResourceMessages.getMessage("error.dao.filter"));
		}
		super.buildWhere(filter, sql, params);
		sql.append(" AND code.disabled = 0");
		if (filter.containsKey("subtype")) {
			params.addValue("subtype", filter.get("subtype"));
			sql.append(" AND subtype = :subtype");
		}
		//Получение списка родительских кодов + codeId
		if (filter.containsKey("codeId")) {
			params.addValue("codeId", filter.get("codeId"));
			sql.append(" AND code.id in (select id from SdGetRepCodes(:codeId, 1))");
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