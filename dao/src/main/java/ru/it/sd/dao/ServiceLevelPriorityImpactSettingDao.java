package ru.it.sd.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.model.ServiceLevelPriorityImpactSetting;

import java.util.List;
import java.util.Map;

/**
 * Дао для настроек условия приоритета по отношению к приоритету сущности
 *
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class ServiceLevelPriorityImpactSettingDao extends AbstractEntityDao<ServiceLevelPriorityImpactSetting> {

    private static final String BASE_SQL =
            "SELECT \n" +
            "   pis.pis_pri_oid, " +
            "   pis.pis_imp_oid, " +
            "   pis.pis_sel_oid, " +
            "   pis.pis_oid\n" +
            "FROM itsm_priorityimpactsettings pis\n";

    private RowMapper<ServiceLevelPriorityImpactSetting> mapper;

    public ServiceLevelPriorityImpactSettingDao(RowMapper<ServiceLevelPriorityImpactSetting> mapper) {
        this.mapper = mapper;
    }

    @Override
    protected StringBuilder getBaseSql() {
        return new StringBuilder(BASE_SQL);
    }

    @Override
    protected List<ServiceLevelPriorityImpactSetting> executeQuery(String sql, SqlParameterSource params) {
        return namedJdbc.query(sql, params, mapper);
    }

    @Override
    protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
        super.buildWhere(filter, sql, params);
        if (filter.containsKey("serviceLevelId")) {
            params.addValue("serviceLevelId", filter.get("serviceLevelId"));
            sql.append(" AND pis.pis_sel_oid = :serviceLevelId");
        }
    }
}