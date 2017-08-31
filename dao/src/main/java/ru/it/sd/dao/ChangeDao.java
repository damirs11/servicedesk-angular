package ru.it.sd.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ChangeExtractor;
import ru.it.sd.exception.BadRequestException;
import ru.it.sd.model.Change;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Дао для работы с изменениями
 *
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class ChangeDao extends AbstractEntityDao<Change> {

	@Autowired
	private ChangeExtractor extractor;

	private static final String BASE_SQL =
			"SELECT \n" +
			"ch.cha_oid, ch.cha_id, ch.cha_description, ch.cha_requestor_per_oid,\n" +
			"ch.cha_per_man_oid, ci.chi_information, ch.cha_sta_oid, ch.cha_imp_oid,\n" +
			"ch.reg_created, ch.cha_deadline, ch.cha_actualfinish, ch.ass_per_to_oid,\n" +
            "ch.ass_wog_oid, ch.cha_cat_oid, ch.cha_cla_oid \n" +
			"FROM\n" +
			"itsm_changes ch\n" +
			"LEFT JOIN itsm_cha_information ci ON ci.chi_cha_oid = ch.cha_oid\n";

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<Change> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, extractor);
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		if (Objects.isNull(filter) || filter.isEmpty()) {
			return;
		}
		super.buildWhere(filter, sql, params);

		if (filter.containsKey("personId")) {
			long personId = Long.valueOf(filter.get("personId"));
			params.addValue("personId", personId);

			String condition = filter.get("filter");
			if (Objects.nonNull(condition)) {
				switch (condition) {
					case "executor": {
						sql.append(" AND ch.ass_per_to_oid = :personId");
						break;
					}
					case "approver": {
						//todo ???
						break;
					}
					case "initiator": {
						sql.append(" AND ch.cha_requestor_per_oid = :personId");
						break;
					}
					case "manager": {
						sql.append(" AND ch.cha_per_man_oid = :personId");
						break;
					}
					default: {
						if (condition.startsWith("group_")) {
							String s = StringUtils.split(condition, '_')[1];
							long id = Long.valueOf(s);
							//todo условие на группу
						} else {
							throw new BadRequestException(MessageFormat.format("Неправильно указано значение фильтра: {0}", condition));
						}
					}
				}
			} else {
				// Когда указан пользоователь, но не указан фильтр по нему, тогда объединяем множества
				sql.append(" AND (ch.ass_per_to_oid = :personId");
				//todo approver ???
				sql.append(" OR ch.cha_requestor_per_oid = :personId");
				sql.append(" OR ch.cha_per_man_oid = :personId)");
			}
		}
	}
}