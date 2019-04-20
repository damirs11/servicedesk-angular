package ru.it.sd.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ChangeExtractor;
import ru.it.sd.dao.utils.AccessFilterEntity;
import ru.it.sd.dao.utils.FilterMap;
import ru.it.sd.dao.utils.TemplateUtils;
import ru.it.sd.model.Change;
import ru.it.sd.model.Template;
import ru.it.sd.model.TemplateValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
public class ChangeDao extends AbstractEntityDao<Change> implements Templated<Change>{

	private ChangeExtractor extractor;
	private final TemplateValueDao templateValueDao;

	private static final String BASE_SQL =
			" SELECT\n" +
			"   ch.cha_oid, " +
			"   ch.cha_id, " +
			"   ch.cha_description, " +
			"   ch.cha_requestor_per_oid, " +
			"   ch.cha_per_man_oid, " +
			"   ci.chi_information, " +
			"   ch.cha_sta_oid, " +
			"   ch.cha_solution, " +
			"   ch.cha_imp_oid, " +
			"   ch.reg_created, " +
			"   ch.cha_deadline, " +
			"   ch.cha_actualstart, " +
			"   ch.cha_actualfinish, " +
			"   ch.cha_latefinish, " +
			"   ch.cha_planstart, " +
			"   ch.cha_planfinish, " +
			"   ch.cha_planduration, " +
			"   ch.cha_assign_status ass_status, " +
			"   ch.cha_assign_priority ass_priority, " +
			"   ch.ass_per_to_oid ass_person_to, " +
            "   ch.ass_wog_oid ass_workgroup_to, " +
			"   ch.cha_cat_oid, " +
            "   ch.cha_closurecode, " +
            "   ch.cha_poo_oid, " +
			"   ch.cha_tem_oid, " +
			"   ch.cha_cla_oid, " +
			"   ch.cha_cit_oid, " +
			"   ccu.ccu_changecode1, " +
            "   ccu.ccu_changetext1, " +
            "   ccu.ccu_changetext7, " +
            "   ch.cha_workaround \n" +
			" FROM\n" +
			"   itsm_changes ch\n" +
			"   LEFT JOIN itsm_cha_information ci ON ci.chi_cha_oid = ch.cha_oid\n" +
			"   LEFT JOIN itsm_cha_custom_fields ccu ON ccu.ccu_cha_oid = ch.cha_oid\n";

	public ChangeDao(ChangeExtractor extractor, TemplateValueDao templateValueDao) {
		this.extractor = extractor;
		this.templateValueDao = templateValueDao;
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
			}
			//todo добавить проверку прав доступа (чтение по папкам)
			// алгоритм:
			// по всем ролям пользователя найти список папок, где есть право на чтение типа сущности
			// ищем цепочку папок изменения
			// находим общую цепочку (пересечение), пересекаются минимум в одной "папке" - корне
		}

		// Фильтрация по группе исполнителей
		if (condition.startsWith("group_")) {
			String s = StringUtils.split(condition, '_')[1];
			long groupId = Long.valueOf(s);
			params.addValue("groupId", groupId);
			sql.append(" AND (ch.ass_wog_oid in (SELECT id FROM SdGetWorkGroups(:groupId, 0)))");
		}
		if (filter instanceof FilterMap) {
			List<AccessFilterEntity> accessFilter = ((FilterMap) filter).getAccessFilter();
			if (!accessFilter.isEmpty()) {
				sql.append(" AND ( ");
				if (accessFilter.size() == 1 && accessFilter.get(0).getNoAccess()) {
					sql.append("1=0");
				} else {
					boolean isFirst = true;
					for (AccessFilterEntity filterEntity : accessFilter) {
						sql.append(isFirst ? " ( 1=1 " : " OR ( 1=1");
						if (!filterEntity.getFolders().isEmpty()) {
							sql.append(" AND ch.cha_poo_oid in (").append(filterEntity.getFoldersString()).append(")");
						}
						if (filterEntity.getExecutor() != null) {
							sql.append(" AND ch.ass_per_to_oid = ").append(filterEntity.getExecutor().toString()).append(" ");
						}
						if (!filterEntity.getWorkgroups().isEmpty()) {
							sql.append(" AND ch.ass_wog_oid in (").append(filterEntity.getWorkgroupsString()).append(") ");
						}
						sql.append(" ) ");
						isFirst = false;
					}
				}
				sql.append(" ) ");
			}
		}
	}

	@Override
	public Change getTemplate(Template template) {
		Map<String, String> filter = new HashMap<>();
		filter.put("templateId", template.getId().toString());
		List<TemplateValue> templateValues = templateValueDao.list(filter);
		try {
			ResultSet resultSet = TemplateUtils.getResultSet(templateValues, template.getEntityType());
			List<Change> changes = extractor.extractData(resultSet);
			return changes != null ? changes.get(0) : null;
		} catch (SQLException e) {
			throw new IllegalArgumentException("Incorrect template :" + template, e);
		}
	}
}