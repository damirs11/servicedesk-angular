package ru.it.sd.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ProblemMapper;
import ru.it.sd.dao.utils.AccessFilterEntity;
import ru.it.sd.dao.utils.FilterMap;
import ru.it.sd.dao.utils.TemplateUtils;
import ru.it.sd.model.Problem;
import ru.it.sd.model.Template;
import ru.it.sd.model.TemplateValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Дао для работы с "Проблемами"
 *
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class ProblemDao extends AbstractEntityDao<Problem> implements Templated<Problem> {

    private ProblemMapper mapper;
    private TemplateValueDao templateValueDao;

    private static final String BASE_SQL =
            "SELECT \n" +
                    "pro_oid, \n" +
                    "pro_id, \n" +
                    "pro_sta_oid, \n" +
                    "pro_requestor_per_oid, \n" +
                    "pro_cit_oid, \n" +
                    "pro_description, \n" +
                    "pri.pri_information, \n" +
                    "pcf.pcf_problemtext4, \n" +
                    "pcf.pcf_problemtext1, \n" +
                    "pcf.pcf_problemtext3, \n" +
                    "pr2.pr2_4k2, \n" +
                    "prs.prs_solution, \n" +
                    "pro_workaround, \n" +
                    "pro_imp_oid, \n" +
                    "pro_deadline, \n" +
                    "pro_actualfinish, \n" +
                    "pro_latefinish, \n" +
                    "pcf.pcf_boolean12, \n" +
                    "pcf.pcf_proshorttext2, \n" +
                    "pro_planfinish, \n" +
                    "pcf.pcf_problemtext2, \n" +
                    "pro_cat_oid, \n" +
                    "pro_cla_oid, \n" +
                    "pro_clo_oid, \n" +
                    "pro_poo_oid, \n" +
                    "pcf.pcf_boolean1, \n" +
                    "pcf.pcf_problemdate2, \n" +
                    "pr1.pr1_4k1, \n" +
                    "pro.pro_assign_status ass_status, \n" +
                    "pro.pro_assign_priority ass_priority, \n" +
                    "pro.ass_per_to_oid ass_person_to, \n" +
                    "pro.ass_wog_oid ass_workgroup_to \n" +
                    "FROM itsm_problems pro \n" +
                    "LEFT JOIN itsm_pro_information pri on pri.pri_pro_oid = pro.pro_oid \n" +
                    "LEFT JOIN itsm_pro_custom_fields pcf ON (pcf.pcf_pro_oid = pro.pro_oid) \n" +
                    "LEFT JOIN itsm_pro_4k2 pr2 ON (pr2.pr2_pro_oid = pro.pro_oid) \n" +
                    "LEFT JOIN itsm_pro_4k1 pr1 ON (pr1.pr1_pro_oid = pro.pro_oid) \n" +
                    "LEFT JOIN itsm_pro_solution prs ON (prs.prs_pro_oid = pro.pro_oid)";

    public ProblemDao(ProblemMapper mapper, TemplateValueDao templateValueDao) {
        this.mapper = mapper;
        this.templateValueDao = templateValueDao;
    }

    @Override
    protected StringBuilder getBaseSql() {
        return new StringBuilder(BASE_SQL);
    }

    @Override
    protected List<Problem> executeQuery(String sql, SqlParameterSource params) {
        return namedJdbc.query(sql, params, (RowMapper<Problem>) mapper);
    }

    @Override
    protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
        if (Objects.isNull(filter) || filter.isEmpty()) {
            return;
        }
        super.buildWhere(filter, sql, params);
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
                            sql.append(" AND pro.pro_poo_oid in (").append(filterEntity.getFoldersString()).append(")");
                        }
                        if (filterEntity.getExecutor() != null) {
                            sql.append(" AND pro.ass_per_to_oid = ").append(filterEntity.getExecutor().toString()).append(" ");
                        }
                        if (!filterEntity.getWorkgroups().isEmpty()) {
                            sql.append(" AND pro.ass_wog_oid in (").append(filterEntity.getWorkgroupsString()).append(") ");
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
    public Problem getTemplate(Template template) {
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