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

/**
 * Интерфейс для поиска кодов.
 */
@Repository
public class CodeChildsDao extends AbstractEntityDao<BaseCode>{

	private CodeMapper mapper;
	private DBUtils dbUtils;

	public CodeChildsDao(CodeMapper mapper, DBUtils dbUtils) {
		this.mapper = mapper;
		this.dbUtils = dbUtils;
	}

	String BASE_SQL =
		"with folder(id) as(\n" +
		"	SELECT rcd.rcd_oid FROM rep_codes rcd WHERE rcd.rcd_rcd_oid = :parentId\n" +
		"	UNION ALL\n" +
		"	SELECT rcd.rcd_oid FROM rep_codes rcd\n" +
		"	INNER JOIN folder ON folder.id = rcd.rcd_rcd_oid\n" +
		")\n" +
		"SELECT " +
				"rcd.rcd_oid id," +
				"rct.rct_name name, " +
				"rcd.rcd_subtype subtype, " +
				"rcd.rcd_ordering ordering " +
		"FROM rep_codes rcd\n" +
		"LEFT JOIN rep_codes_text rct ON rct.rct_rcd_oid = rcd.rcd_oid\n" +
		"WHERE rct.rct_lng_oid = 1049 and rcd.rcd_oid in (SELECT folder.id FROM folder)\n";

	String TEST_BASE_SQL =
			"with recursive folder(id) as(\n" +
			"	SELECT rcd.rcd_oid FROM rep_codes rcd WHERE rcd.rcd_rcd_oid = :parentId\n" +
			"	UNION ALL\n" +
			"	SELECT rcd.rcd_oid FROM rep_codes rcd\n" +
			"	INNER JOIN folder ON folder.id = rcd.rcd_rcd_oid\n" +
			")\n" +
			"SELECT " +
					"rcd.rcd_oid id," +
					"rct.rct_name name, " +
					"rcd.rcd_subtype subtype, " +
					"rcd.rcd_ordering ordering " +
			"FROM rep_codes rcd\n" +
			"LEFT JOIN rep_codes_text rct ON rct.rct_rcd_oid = rcd.rcd_oid\n" +
			"WHERE rct.rct_lng_oid = 1049 and rcd.rcd_oid in (SELECT folder.id FROM folder)\n";

	@Override
	protected StringBuilder getBaseSql() {
		if(dbUtils.isTest()){
			return new StringBuilder(TEST_BASE_SQL);
		}else{
			return new StringBuilder(BASE_SQL);
		}
	}

	@Override
	protected List<BaseCode> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, mapper.asRowMapper());
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		if (filter == null || filter.isEmpty() ||
				!(filter.containsKey("id") || filter.containsKey("parentId"))) {
			throw new ServiceException(ResourceMessages.getMessage("error.dao.filter"));
		}
		if (filter.containsKey("parentId")) {
			params.addValue("parentId", filter.get("parentId"));
		}
	}

}