package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ChangeHistoryMapper;
import ru.it.sd.dao.mapper.ProblemHistoryMapper;
import ru.it.sd.dao.utils.FilterUtils;
import ru.it.sd.model.ChangeHistory;
import ru.it.sd.model.ProblemHistory;

import java.util.List;
import java.util.Map;

/**
 * Дао для работы с данными записей истории изменений
 */
@Repository
public class ProblemHistoryDao extends AbstractHistoryDao<ProblemHistory> {

	@Autowired
	private ProblemHistoryMapper mapper;

	/**
	 * Общий запрос получения данных о записи в истории
	 */
	private static final String BASE_SQL =
		" SELECT " +
			"hpr.hpr_oid, " +
			"hpr.hpr_subject, " +
			"hpr.reg_created_by_oid, " +
			"hpr.hpr_created, " +
			"hpr.hpr_newvalue," +
			"hpr.hpr_valueatr_oid " +
		" FROM " +
			"itsm_historylines_problem AS hpr";

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<ProblemHistory> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (RowMapper<ProblemHistory>) mapper);
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
				sql.append(" AND hpr.hpr_pro_oid = :entityId");
			}
			// Фильтр, оставляющий только записи, относящиеся к чату (или наоборот)
			if (filter.containsKey("chat")) {
				sql.append(" AND hpr.hpr_valueatr_oid ");

				String value = filter.get("chat");
				if (!FilterUtils.getFlagValue(value)) {
					sql.append("NOT ");
				}
				// todo заменить магические числа
				// todo для проблем они могут быть другими!!! проверить
				sql.append("IN ").append("(724041771, 281484032738115, 165993) ");
				sql.append(" AND hpr.hpr_newvalue is not null");
			}
		}
	}
}