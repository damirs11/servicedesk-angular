package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ServicecallHistoryMapper;
import ru.it.sd.dao.utils.FilterUtils;
import ru.it.sd.model.HistoryType;
import ru.it.sd.model.ServicecallHistory;

import java.util.List;
import java.util.Map;

/**
 * Дао для работы с данными записей истории изменений
 */
@Repository
public class ServicecallHistoryDao extends AbstractHistoryDao<ServicecallHistory> {

	@Autowired
	private ServicecallHistoryMapper mapper;

	/**
	 * Общий запрос получения данных о записи в истории
	 */
	private static final String BASE_SQL =
		" SELECT " +
			"hsc.hsc_oid, " +
			"hsc.hsc_subject, " +
			"hsc.reg_createdby_oid, " +
			"hsc.hsc_created, " +
			"hsc.hsc_newvalue," +
			"hsc.hsc_valueatr_oid " +
		" FROM " +
			"itsm_historylines_servicecall AS hsc";

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<ServicecallHistory> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (RowMapper<ServicecallHistory>) mapper);
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		if (filter == null) {
			return;
		}
		super.buildWhere(filter, sql, params);
		// Если фильтруем запрос списка
		if (!filter.containsKey("id")) {
			checkFilterRequirements(filter);
			// Проверяем необходимость получения истории конкретной сущности
			if (filter.containsKey("entityId")) {
				params.addValue("entityId", filter.get("entityId"));
				sql.append(" AND hsc.hsc_ser_oid = :entityId");
			}
			// Фильтр, оставляющий только записи, относящиеся к чату (или наоборот)
			if (filter.containsKey("chat")) {
				sql.append(" AND hsc.hsc_valueatr_oid ");

				String value = filter.get("chat");
				if (!FilterUtils.getFlagValue(value)) {
					sql.append("NOT ");
				}
				// todo заменить магические числа
				sql.append("IN ").append("(")
						.append(HistoryType.SERVICECALL_INITIATOR.getFieldId())
						.append(", ")
						.append(HistoryType.SERVICECALL_EXECUTOR.getFieldId())
						.append(") ");
				sql.append(" AND hsc.hsc_newvalue is not null");
			}
		}
	}
//
//	@Override
//	protected void buildOrderBy(Map<String, String> filter, StringBuilder sql, int insertPos) {
//		super.buildOrderBy(filter, sql, insertPos);
//		sql.append(" hch.reg_created ");
//	}
}