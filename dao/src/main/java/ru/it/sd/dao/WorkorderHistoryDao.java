package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.WorkorderHistoryMapper;
import ru.it.sd.dao.utils.FilterUtils;
import ru.it.sd.model.WorkorderHistory;

import java.util.List;
import java.util.Map;

/**
 * Дао для работы с данными записей истории изменений
 */
@Repository
public class WorkorderHistoryDao extends AbstractHistoryDao<WorkorderHistory> {

	@Autowired
	private WorkorderHistoryMapper mapper;

	/**
	 * Общий запрос получения данных о записи в истории
	 */
	private static final String BASE_SQL =
		" SELECT " +
			"hwk.hwk_oid, " +
			"hwk.hwk_subject, " +
			"hwk.reg_created_by_oid, " +
			"hwk.reg_created, " +
			"hwk.hwk_newvalue," +
			"hwk.hwk_valueatr_oid " +
		" FROM " +
			"itsm_historylines_workorder AS hwk";

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<WorkorderHistory> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (RowMapper<WorkorderHistory>) mapper);
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		super.buildWhere(filter, sql, params);
		// Если фильтруем запрос списка
		if (!filter.containsKey("id")) {
			checkFilterRequirements(filter);
			// Проверяем необходимость получения истории конкретной сущности
			if (filter.containsKey("entityId")) {
				params.addValue("entityId", filter.get("entityId"));
				sql.append(" AND hwk.hwk_wor_oid = :entityId");
			}
			// Фильтр, оставляющий только записи, относящиеся к чату (или наоборот)
			if (filter.containsKey("chat")) {
				sql.append(" AND hwk.hwk_valueatr_oid ");

				String value = filter.get("chat");
				if (!FilterUtils.getFlagValue(value)) {
					sql.append("NOT ");
				}

				sql.append("IN ").append("(281479977894277, 1082392634) ");
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