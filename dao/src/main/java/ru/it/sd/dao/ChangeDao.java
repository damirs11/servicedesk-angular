package ru.it.sd.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ChangeExtractor;
import ru.it.sd.model.Change;

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

	private ChangeExtractor extractor;

	private static final String BASE_SQL =
			" SELECT\n" +
			"   ch.cha_oid, " +
			"   ch.cha_id, " +
			"   ch.cha_description, " +
			"   ch.cha_requestor_per_oid, " +
			"   ch.cha_per_man_oid, " +
			"   ci.chi_information, " +
			"   ch.cha_sta_oid, " +
			"   ch.cha_imp_oid, " +
			"   ch.reg_created, " +
			"   ch.cha_deadline, " +
			"   ch.cha_actualfinish, " +
			"   ch.ass_per_to_oid, " +
            "   ch.ass_wog_oid, " +
			"   ch.cha_cat_oid, " +
            "   ch.cha_closurecode, " +
            "   ch.cha_poo_oid, " +
			"   ch.cha_tem_oid, " +
			"   ch.cha_cla_oid\n" +
			" FROM\n" +
			"   itsm_changes ch\n" +
			"   LEFT JOIN itsm_cha_information ci ON ci.chi_cha_oid = ch.cha_oid\n" +
			"   LEFT JOIN itsm_workgroups wg1 ON wg1.wog_oid = ch.ass_wog_oid\n" +
			"   LEFT JOIN itsm_workgroups wg2 ON wg2.wog_oid = wg1.wog_parent\n" +
			"   LEFT JOIN itsm_workgroups wg3 ON wg3.wog_oid = wg2.wog_parent\n" +
			"   LEFT JOIN itsm_workgroups wg4 ON wg4.wog_oid = wg3.wog_parent\n"; // смотрим четыре уровня групп

	public ChangeDao(ChangeExtractor extractor) {
		this.extractor = extractor;
	}

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

		// Фильтр по умолчанию
		String condition = StringUtils.defaultString(filter.get("filter"), "default");
		// Обработка персональных фильтров
		if (filter.containsKey("personId")) {
			long personId = Long.valueOf(filter.get("personId"));
			params.addValue("personId", personId);

			switch (condition) {
				case "executor": {
					sql.append(" AND ch.ass_per_to_oid = :personId");
					break;
				}
				case "approver": {
					sql.append(" AND :personId IN (SELECT apv_per_oid FROM " +
							" itsm_approver_votes WHERE apv_apt_oid = ch.cha_oid)");
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
					// Добавляем все условия через OR
					sql.append(" AND (");
					sql.append(" ch.ass_per_to_oid = :personId");

					sql.append(" OR :personId IN (SELECT apv_per_oid FROM " +
							" itsm_approver_votes WHERE apv_apt_oid = ch.cha_oid)");

					sql.append(" OR ch.cha_requestor_per_oid = :personId");

					sql.append(" OR ch.cha_per_man_oid = :personId");
					sql.append(')');
				}
			}
		}

		// Фильтрация по группе исполнителей
		if (condition.startsWith("group_")) {
			String s = StringUtils.split(condition, '_')[1];
			long groupId = Long.valueOf(s);
			params.addValue("groupId", groupId);
			sql.append(" AND (:groupId = wg1.wog_oid OR " +
							 ":groupId = wg2.wog_oid OR " +
					         ":groupId = wg3.wog_oid OR " +
					         ":groupId = wg4.wog_oid)");
		}
	}
}