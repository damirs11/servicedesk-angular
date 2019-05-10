package ru.it.sd.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.ServiceLevelPriorityDuration;
import ru.it.sd.model.ServiceLevelPriorityDurationType;
import ru.it.sd.util.ResourceMessages;

import java.util.List;
import java.util.Map;

/**
 * Дао для работы с максимальной продолжительностью условий приоритета
 *
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class ServiceLevelPriorityDurationDao extends AbstractEntityDao<ServiceLevelPriorityDuration> {

	private static final String BASE_SQL =
			"SELECT " +
			"	pds.pds_oid, " +
			"	pds.pds_maximumduration, " +
			"	pds.pds_priority\n" +
			"FROM itsm_prioritydurationsettings pds\n";

	private final EntityRowMapper<ServiceLevelPriorityDuration> mapper;

	public ServiceLevelPriorityDurationDao(EntityRowMapper<ServiceLevelPriorityDuration> mapper) {
		this.mapper = mapper;
	}

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<ServiceLevelPriorityDuration> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, mapper.asRowsExtractor());
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		super.buildWhere(filter, sql, params);
		if (filter == null || filter.isEmpty()) {
			throw new ServiceException(ResourceMessages.getMessage("error.dao.filter"));
		}
		if (filter.containsKey("entityTypeId")) {
			ServiceLevelPriorityDurationType priorityDurationType = ServiceLevelPriorityDurationType.findByEntityType(Long.valueOf(filter.get("entityTypeId")));
			params.addValue("durationTypeId", priorityDurationType.getType());
			sql.append(" AND pds.pds_entity = :durationTypeId");
		}
		if (filter.containsKey("serviceLevelPriorityId")) {
			params.addValue("serviceLevelPriorityId", filter.get("serviceLevelPriorityId"));
			sql.append(" AND pds.pds_priority = :serviceLevelPriorityId");
		}
	}
}