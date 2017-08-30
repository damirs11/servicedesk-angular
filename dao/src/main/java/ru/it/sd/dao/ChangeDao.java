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
			"SELECT\n" +
					"ch.cha_oid, ch.cha_id, ch.cha_description, ch.cha_requestor_per_oid,\n" +
					"ch.cha_per_man_oid, ci.chi_information, ch.cha_sta_oid, ch.cha_imp_oid,\n" +
					"ch.reg_created, ch.cha_deadline, ch.cha_actualfinish, ch.ass_per_to_oid, ch.ass_wog_oid\n" +
					"FROM\n" +
					"itsm_changes ch\n" +
					"LEFT JOIN itsm_cha_information ci ON ci.chi_cha_oid = ch.cha_oid\n" +
					"LEFT JOIN itsm_workgroups wog1 ON (wog1.wog_oid = ch.ass_wog_oid)\n" +
					"LEFT JOIN itsm_workgroups wog2 ON (wog2.wog_oid = wog1.wog_parent)\n" +
					"LEFT JOIN itsm_workgroups wog3 ON (wog3.wog_oid = wog2.wog_parent)\n" +
					"LEFT JOIN itsm_workgroups wog4 ON (wog4.wog_oid = wog3.wog_parent)";

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
						sql.append(" AND EXISTS(SELECT v.apv_oid FROM itsm_approver_votes v")
								.append(" WHERE v.apv_apt_oid = ch.cha_oid AND")
								.append(" v.apv_per_oid = :personId)");
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
							long groupId = Long.valueOf(s);
							params.addValue("groupId", groupId);
							sql.append(" AND EXISTS(SELECT m.mem_oid FROM")
									.append(" itsm_members m")
									.append(" WHERE m.mem_per_oid = :personId ")
									.append(" AND m.mem_wog_oid IN (wog1.wog_oid, wog2.wog_oid, wog3.wog_oid, wog4.wog_oid)")
									.append(" AND :groupId IN (wog1.wog_oid, wog2.wog_oid, wog3.wog_oid, wog4.wog_oid)")
									.append(")");
						} else {
							throw new BadRequestException(MessageFormat.format("Неправильно указано значение фильтра: {0}", condition));
						}
					}
				}
			} else {
				// Когда указан пользователь, но не указан фильтр по нему, тогда объединяем множества
				sql.append(" AND (ch.ass_per_to_oid = :personId") // исполнитель
						// согласующий
						.append(" OR EXISTS(SELECT v.apv_oid FROM itsm_approver_votes v")
						.append(" WHERE v.apv_apt_oid = ch.cha_oid AND")
						.append(" v.apv_per_oid = :personId)")
						// инициатор
						.append(" OR ch.cha_requestor_per_oid = :personId")
						// менеджер
						.append(" OR ch.cha_per_man_oid = :personId")
						// группы
						.append(" OR EXISTS(SELECT m.mem_oid FROM")
						.append(" itsm_members m")
						.append(" WHERE m.mem_per_oid = :personId ")
						.append(" AND m.mem_wog_oid IN (wog1.wog_oid, wog2.wog_oid, wog3.wog_oid, wog4.wog_oid))")
						.append(')');
			}
		}
	}
}