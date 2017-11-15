package ru.it.sd.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.CodeMapper;
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

	public CodeDao(CodeMapper mapper) {
		this.mapper = mapper;
	}

	String BASE_SQL =
		"SELECT id, name FROM\n" +
		"(SELECT\n" +
		"	cod.cod_oid id,\n" +
		"	cdl.cdl_name name\n" +
		"   cod.cod_subtype subtype\n" +
		"   cod.cod_ordering ordering\n" +
		"FROM\n" +
		"	itsm_codes cod\n" +
		"   LEFT JOIN itsm_codes_locale cdl ON (cdl.cdl_cod_oid = cod.cod_oid AND cdl.cdl_lng_oid = 1049)\n" +
		"WHERE cod.cod_disabled = 0\n" +
		"SELECT\n" +
		"	rcd.rcd_oid id,\n" +
		"	rct.rct_name name\n" +
		"   rcd.rcd_subtype subtype\n" +
		"   rcd.rcd_ordering ordering\n" +
		"FROM\n" +
		"	rep_codes rcd\n" +
		"	LEFT JOIN rep_codes_text rct ON (rct.rct_rcd_oid = rcd.rcd_oid AND rct.rct_lng_oid = 1049)\n" +
		"WHERE rcd.rcd_codedisabled = 0)\n";

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
				!(filter.containsKey("id") || filter.containsKey("subtype"))) {
			throw new ServiceException(ResourceMessages.getMessage("error.dao.filter"));
		}
		super.buildWhere(filter, sql, params);
		if (filter.containsKey("subtype")) {
			params.addValue("subtype", filter.get("subtype"));
			sql.append(" AND subtype = :subtype");
		}
	}

	@Override
	protected void buildOrderBy(Map<String, String> filter, StringBuilder sql, int insertPos) {
		if (!filter.containsKey(SORTING_PARAM_NAME)) {
			filter.put(SORTING_PARAM_NAME, "ordering");
		}
		super.buildOrderBy(filter, sql, insertPos);
	}
}