package ru.it.sd.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.mapper.servicelevel.ServiceLevelListMapper;
import ru.it.sd.dao.mapper.servicelevel.ServiceLevelMapper;
import ru.it.sd.dao.mapper.servicelevel.ServiceLevelSimpleMapper;
import ru.it.sd.model.ServiceLevel;

import java.util.List;
import java.util.Map;

/**
 * Дао для работы с ролями
 *
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class ServiceLevelDao extends AbstractEntityDao<ServiceLevel> {

    private static final String BASE_SQL =
            "SELECT \n" +
            "   sel.sel_name, " +
            "   sel.sel_oid, " +
            "   sel.sel_description, " +
            "   sel.sel_default, " +
            "   sel.sel_blocked\n" +
            "FROM itsm_service_level sel\n";

    private final ServiceLevelMapper mapper;
    private final ServiceLevelListMapper serviceLevelListMapper;
    private final ServiceLevelSimpleMapper serviceLevelSimpleMapper;

    public ServiceLevelDao(ServiceLevelMapper mapper, ServiceLevelListMapper serviceLevelListMapper, ServiceLevelSimpleMapper serviceLevelSimpleMapper) {
        this.mapper = mapper;
        this.serviceLevelListMapper = serviceLevelListMapper;
        this.serviceLevelSimpleMapper = serviceLevelSimpleMapper;
    }

    @Override
    protected StringBuilder getBaseSql() {
        return new StringBuilder(BASE_SQL);
    }

    @Override
    protected List<ServiceLevel> executeQuery(String sql, SqlParameterSource params) {
        return namedJdbc.query(sql, params, mapper.asRowsExtractor());
    }

    @Override
    protected List<ServiceLevel> executeQuery(String sql, SqlParameterSource params, MapperMode mode) {
        EntityRowMapper<ServiceLevel> entityRowMapper;
        switch (mode) {
            case SIMPLEST:
                entityRowMapper = serviceLevelSimpleMapper;
                break;
            case LIST:
                entityRowMapper = serviceLevelListMapper;
                break;
            case FULL:
            default:
                entityRowMapper = mapper;
                break;
        }
        return namedJdbc.query(sql, params, entityRowMapper.asRowsExtractor());
    }

    @Override
    protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
        super.buildWhere(filter, sql, params);
    }
}