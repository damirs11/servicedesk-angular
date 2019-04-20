package ru.it.sd.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.WorkorderMapper;
import ru.it.sd.dao.utils.AccessFilterEntity;
import ru.it.sd.dao.utils.FilterMap;
import ru.it.sd.dao.utils.TemplateUtils;
import ru.it.sd.model.Template;
import ru.it.sd.model.TemplateValue;
import ru.it.sd.model.Workorder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Дао для работы с данными нарядов
 */
@Repository
public class WorkorderDao extends AbstractEntityDao<Workorder> implements Templated<Workorder>{

	private WorkorderMapper mapper;
	private TemplateValueDao templateValueDao;
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
            "   w.wor_latefinish, " +
			"   w.wor_description, " +
			"   winfo.woi_information, " +
			"   w.wor_sta_oid, " +
			"   w.wor_cat_oid, " +
			"   w.wor_clo_oid, " +
            "   wcustom.wcf_worshorttext3, " +
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
			"   w.wor_pro_oid, " +
            "   wcustom.wcf_workordertext1, " +
            "   wcustom.wcf_workordertext2, " +
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

	public WorkorderDao(WorkorderMapper mapper, TemplateValueDao templateValueDao) {
		this.mapper = mapper;
		this.templateValueDao = templateValueDao;
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
                //todo добавить проверку прав доступа (чтение по папкам)
                // алгоритм:
                // по всем ролям пользователя найти список папок, где есть право на чтение типа сущности
                // ищем цепочку папок изменения
                // находим общую цепочку (пересечение), пересекаются минимум в одной "папке" - корне
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
							sql.append(" AND w.wor_poo_oid in (").append(filterEntity.getFoldersString()).append(")");
						}
						if (filterEntity.getExecutor() != null) {
							sql.append(" AND w.ass_per_to_oid = ").append(filterEntity.getExecutor().toString()).append(" ");
						}
						if (!filterEntity.getWorkgroups().isEmpty()) {
							sql.append(" AND w.ass_workgroup in (").append(filterEntity.getWorkgroupsString()).append(") ");
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
	public Workorder getTemplate(Template template) {
		Map<String, String> filter = new HashMap<>();
		filter.put("templateId", template.getId().toString());
		List<TemplateValue> templateValues = templateValueDao.list(filter);
		try {
		    ResultSet resultSet = TemplateUtils.getResultSet(templateValues, template.getEntityType());
			return mapper.mapRow(resultSet, 0);
		} catch (SQLException e) {
			throw new IllegalArgumentException("Incorrect template :" + template, e);
		}
	}
}