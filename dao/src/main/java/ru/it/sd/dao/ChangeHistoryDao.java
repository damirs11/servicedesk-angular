package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ChangeHistoryMapper;
import ru.it.sd.dao.utils.FilterUtils;
import ru.it.sd.model.ChangeHistory;

import java.util.List;
import java.util.Map;

/**
 * Дао для работы с данными записей истории изменений
 */
@Repository
public class ChangeHistoryDao extends AbstractHistoryDao<ChangeHistory> {

	@Autowired
	private ChangeHistoryMapper mapper;

	/**
	 * Общий запрос получения данных о записи в истории
	 */
	private static final String BASE_SQL =
		" SELECT " +
			"hch.hch_oid, " +
			"hch.hch_subject, " +
			"hch.reg_created_by_oid, " +
			"hch.reg_created, " +
			"hch.hch_newvalue," +
			"hch.hch_valueatr_oid " +
		" FROM " +
			"itsm_historylines_change AS hch";

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<ChangeHistory> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (RowMapper<ChangeHistory>) mapper);
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
				sql.append(" AND hch.hch_cha_oid = :entityId");
			}
			// Фильтр, оставляющий только записи, относящиеся к чату (или наоборот)
			if (filter.containsKey("chat")) {
				sql.append(" AND hch.hch_valueatr_oid ");

				String value = filter.get("chat");
				if (!FilterUtils.getFlagValue(value)) {
					sql.append("NOT ");
				}
				// todo заменить магические числа
				sql.append("IN ").append("(724041771, 281484032738115) ");
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