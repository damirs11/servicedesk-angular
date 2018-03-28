package ru.it.sd.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.TemplateMapper;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.Template;
import ru.it.sd.util.ResourceMessages;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс для поиска кодов.
 */
@Repository
public class TemplateDao extends AbstractEntityDao<Template>{

	private TemplateMapper mapper;

	public TemplateDao(TemplateMapper mapper) {
		this.mapper = mapper;
	}

	String BASE_SQL =
		"SELECT \n" +
		"	DISTINCT(tem_oid)," +
		"	tem_name," +
		"	tem_ent_oid," +
		"	tem_rcd_oid\n" +
		"FROM rep_templates tem\n" +
		"LEFT JOIN rep_template_access tac ON tac.tac_tem_oid = tem_oid\n";

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<Template> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, mapper.asRowMapper());
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		if (filter == null || filter.isEmpty() ||
				!(filter.containsKey("id") || filter.containsKey("entityId") || filter.containsKey("accountId"))) {
			throw new ServiceException(ResourceMessages.getMessage("error.dao.filter"));
		}
		super.buildWhere(filter, sql, params);
		if (filter.containsKey("entityId")) {
			params.addValue("entityId", filter.get("entityId"));
			sql.append("	AND tem_ent_oid = :entityId\n");
		}
		//Фильтр по акканту
		if (filter.containsKey("accountId")) {
			params.addValue("accountId", filter.get("accountId"));
			sql.append("	AND tac.tac_rol_oid in (SELECT DISTINCT t.id FROM rep_roles_per_account ra CROSS APPLY SdGetRoles(ra.rpa_rol_oid, 0) t WHERE ra.rpa_acc_oid = :accountId)\n");
		}
	}

}