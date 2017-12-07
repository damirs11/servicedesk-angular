package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ChangeHistoryLineMapper;
import ru.it.sd.model.ChangeHistoryLine;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Дао для работы с данными записей истории изменений
 */
@Repository
public class ChangeHistoryLineDao extends AbstractEntityDao<ChangeHistoryLine> {

	@Autowired
	private ChangeHistoryLineMapper mapper;

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
	protected List<ChangeHistoryLine> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (RowMapper<ChangeHistoryLine>) mapper);
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		super.buildWhere(filter, sql, params);

		// Фильтр по изменению, то есть получаем все записи в истории для конкретного изменения
		if (Objects.nonNull(filter) && filter.containsKey("entityId")) {
			params.addValue("entityId", filter.get("entityId"));
			sql.append(" AND hch.hch_cha_oid = :entityId");
		}

		// Фильтр, оставляющий только записи, относящиеся к чату (или наоборот)
		if (Objects.nonNull(filter) && filter.containsKey("chat")) {
			String chatFilter = "(724041771,281484032738115) ";
			String value = filter.get("chat");
			if (Objects.equals(value, "false") || Objects.equals(value, "0")) {
				sql.append(" AND hch.hch_valueatr_oid NOT IN ").append(chatFilter);
			} else {
				sql.append(" AND hch.hch_valueatr_oid IN ").append(chatFilter);
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