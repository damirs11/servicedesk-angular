package ru.it.sd.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.code.CodeMapper;
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
@Deprecated
public class CodeChildsDao extends AbstractEntityDao<BaseCode>{

	private CodeMapper mapper;
	private DBUtils dbUtils;

	public CodeChildsDao(CodeMapper mapper, DBUtils dbUtils) {
		this.mapper = mapper;
		this.dbUtils = dbUtils;
	}



	String BASE_SQL =
			"with %s folder(id) as(\n" +
			"	SELECT rcd.rcd_rcd_oid FROM rep_codes rcd WHERE rcd.rcd_oid = :folderId\n" +
			"	UNION ALL\n" +
			"	SELECT rcd.rcd_rcd_oid FROM rep_codes rcd\n" +
			"	INNER JOIN folder ON folder.id = rcd.rcd_oid\n" +
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
			return new StringBuilder(String.format(BASE_SQL, "recursive"));
		}else{
			return new StringBuilder(String.format(BASE_SQL, ""));
		}
	}

	@Override
	protected List<BaseCode> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, mapper.asRowMapper());
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		if (filter == null || filter.isEmpty() ||
				!(filter.containsKey("id") || filter.containsKey("folderId"))) {
			throw new ServiceException(ResourceMessages.getMessage("error.dao.filter"));
		}
		//Получение всех родителей папки
		if (filter.containsKey("folderId")) {
			params.addValue("folderId", filter.get("folderId"));
		}
	}

}