package ru.it.sd.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.WorkorderMapper;
import ru.it.sd.model.Workorder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Дао для работы с данными нарядов
 */
@Repository
public class WorkorderDao extends AbstractEntityDao<Workorder> {

	private WorkorderMapper mapper;

	/**
	 * Общий запрос получения данных о наряде
	 */

	private static final String BASE_SQL =
			" SELECT\n" +
			"   w.wor_oid, " +
			"   w.wor_id, " +
			"   w.reg_created, " +
			"   w.wor_deadline, " +
			"   w.reg_modified, " +
			"   w.wor_actualfinish, " +
			"   w.wor_description, " +
			"   winfo.woi_information, " +
			"   w.wor_sta_oid, " +
			"   w.wor_cat_oid, " +
			"   w.wor_clo_oid, " +
			"   w.wor_poo_oid, " +
			"   wcustom.wcf_boolean2, " +
			"   w.wor_requestor_per_oid, " +
			"   w.ass_assignstatus ass_status, " +
			"   w.wor_assignpriority ass_priority, " +
			"   w.ass_workgroup ass_workgroup_to, " +
			"   w.ass_per_to_oid ass_person_to, " +
			"   wcustom.wcf_duration1, " +
			"   wor4k1.wo1_4k1, " +
			"   w.wor_ser_oid, " +
			"   w.wor_cha_oid, " +
			"   wcustom.wcf_org1_oid\n" +
			" FROM\n" +
			"   itsm_workorders AS w " +
			"   LEFT JOIN itsm_wor_information AS winfo ON winfo.woi_wor_oid = w.wor_oid\n" +
			"   LEFT JOIN itsm_wor_custom_fields AS wcustom ON wcustom.wcf_wor_oid = w.wor_oid\n" +
			"   LEFT JOIN itsm_wor_4k1 AS wor4k1 ON wor4k1.wo1_wor_oid = w.wor_oid\n" +
			"   LEFT JOIN itsm_workgroups wg1 ON wg1.wog_oid = w.ass_workgroup\n" +
			"   LEFT JOIN itsm_workgroups wg2 ON wg2.wog_oid = wg1.wog_parent\n" +
			"   LEFT JOIN itsm_workgroups wg3 ON wg3.wog_oid = wg2.wog_parent\n" +
			"   LEFT JOIN itsm_workgroups wg4 ON wg4.wog_oid = wg3.wog_parent\n"; // смотрим четыре уровня групп

	public WorkorderDao(WorkorderMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<Workorder> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, mapper.asRowMapper());
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
					sql.append(" AND w.ass_per_to_oid = :personId");
					break;
				}
				case "approver": {
					sql.append(" AND :personId IN (SELECT apv_per_oid FROM " +
							" itsm_approver_votes WHERE apv_apt_oid = w.wor_oid)");
					break;
				}
				case "initiator": {
					sql.append(" AND w.wor_requestor_per_oid = :personId");
					break;
				}
				default: {
					// Добавляем все условия через OR
					sql.append(" AND (");
					sql.append(" w.ass_per_to_oid = :personId");

					sql.append(" OR :personId IN (SELECT apv_per_oid FROM " +
							" itsm_approver_votes WHERE apv_apt_oid = w.wor_oid)");

					sql.append(" OR w.wor_requestor_per_oid = :personId");

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