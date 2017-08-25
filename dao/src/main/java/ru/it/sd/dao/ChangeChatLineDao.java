package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ChangeChatLineMapper;
import ru.it.sd.dao.mapper.ChangeChatLineMapper;
import ru.it.sd.model.ChangeChatLine;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Дао для работы с данными сообщений
 */
@Repository
public class ChangeChatLineDao extends AbstractEntityDao<ChangeChatLine> {

	@Autowired
	private ChangeChatLineMapper mapper;

	/**
	 * Общий запрос получения данных о сообщения
	 */
	private static final String BASE_SQL =
		"SELECT " +
			"hch.hch_oid, " +
			"hch.hch_newvalue, " +
			"hch.reg_created_by_oid, " +
			"hch.reg_created, " +
			"hch.hch_valueatr_oid " +
		"FROM " +
			"itsm_historylines_change AS hch";

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<ChangeChatLine> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (RowMapper<ChangeChatLine>) mapper);
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		super.buildWhere(filter, sql, params);

		// Фильтр по изменению, то есть получаем все записи в истории для конкретного изменения
		if (Objects.nonNull(filter) && filter.containsKey("entity")) {
			params.addValue("entity", filter.get("entity"));
			sql.append(" AND hch.hch_cha_oid = :entity");
		}

	}
}