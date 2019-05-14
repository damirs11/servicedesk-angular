package ru.it.sd.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.RequireField;
import ru.it.sd.util.ResourceMessages;

import java.util.List;
import java.util.Map;

/**
 * Дао для получения списка обязательных полей
 *
 * @author nsychev
 * @since 14.05.2019
 */
@Repository
public class RequiredFieldDao extends AbstractEntityDao<RequireField> {

	private static final String BASE_SQL =
			"SELECT atr_oid FROM ifc_attributes\n" +
			"LEFT JOIN rep_attr_prop_per_entity ape ON ape.ape_atr_oid = atr_oid and ape.ape_ent_oid = :entityTypeId\n" +
			"LEFT JOIN rep_attr_per_status aps ON aps.aps_atr_oid = atr_oid AND aps.aps_cod_oid = :statusId\n";

	private RowMapper<RequireField> mapper;

	public RequiredFieldDao(RowMapper<RequireField> mapper) {
		this.mapper = mapper;
	}

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<RequireField> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, mapper);
	}

	@Override
	public int count(Map<String, String> filter) {
		throw new UnsupportedOperationException();
	}

	@Override
	public RequireField read(Long id, MapperMode mode) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		super.buildWhere(filter, sql, params);
		if (filter == null || filter.isEmpty() || !filter.containsKey("entityTypeId")) {
			throw new ServiceException(ResourceMessages.getMessage("error.dao.filter"));
		}
		String statusId = "0";
		if (filter.containsKey("statusId")) {
			statusId = filter.get("statusId");
		}
		params.addValue("entityTypeId", filter.get("entityTypeId"));
		params.addValue("statusId", statusId);
		sql.append(" AND (atr_alwaysrequired = 1 OR ape.ape_required = 1 OR aps.aps_oid is not null )");
	}
}