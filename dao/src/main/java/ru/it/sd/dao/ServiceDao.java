package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.mapper.service.ServiceListMapper;
import ru.it.sd.dao.mapper.service.ServiceMapper;
import ru.it.sd.dao.mapper.service.ServiceSimpleMapper;
import ru.it.sd.model.Service;

import java.util.List;
import java.util.Map;

@Component
public class ServiceDao extends AbstractEntityDao<Service> {

    private final ServiceMapper serviceMapper;
    private final ServiceListMapper serviceListMapper;
    private final ServiceSimpleMapper serviceSimpleMapper;

    private static final String BASE_SQL =
            "SELECT \n" +
            "   srv.srv_oid, " +
            "   srv.srv_name, " +
            "   srv.srv_status_cod_oid, " +
            "   srv.srv_pool_cod_oid\n" +
            "FROM itsm_services srv\n";

    @Autowired
    public ServiceDao(ServiceMapper serviceMapper, ServiceListMapper serviceListMapper, ServiceSimpleMapper serviceSimpleMapper) {
        this.serviceMapper = serviceMapper;
        this.serviceListMapper = serviceListMapper;
        this.serviceSimpleMapper = serviceSimpleMapper;
    }

    @Override
    protected StringBuilder getBaseSql() {
        return new StringBuilder(BASE_SQL);
    }

    @Override
    protected List<Service> executeQuery(String sql, SqlParameterSource params) {
        return namedJdbc.query(sql, params, serviceMapper.asRowMapper());
    }

    @Override
    protected List<Service> executeQuery(String sql, SqlParameterSource params, MapperMode mode) {
        EntityRowMapper<Service> entityRowMapper;
        switch (mode) {
            case SIMPLEST:
                entityRowMapper = serviceSimpleMapper;
                break;
            case LIST:
                entityRowMapper = serviceListMapper;
                break;
            case FULL:
            default:
                entityRowMapper = serviceMapper;
                break;
        }
        return namedJdbc.query(sql, params, entityRowMapper.asRowMapper());
    }

    @Override
    protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
        super.buildWhere(filter, sql, params);
    }
}
