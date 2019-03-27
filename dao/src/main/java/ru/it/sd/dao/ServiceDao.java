package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ServiceMapper;
import ru.it.sd.model.Service;

import java.util.List;

@Repository
public class ServiceDao extends AbstractEntityDao<Service> {

    private final ServiceMapper serviceMapper;

    private static final String BASE_SQL =
            "SELECT\n" +
            "   srv.srv_oid, " +
            "   srv.srv_name, " +
            "   srv.srv_pool_cod_oid\n" +
            "FROM itsm_services srv";

    @Autowired
    public ServiceDao(ServiceMapper serviceMapper) {
        this.serviceMapper = serviceMapper;
    }

    @Override
    protected StringBuilder getBaseSql() {
        return new StringBuilder(BASE_SQL);
    }

    @Override
    protected List<Service> executeQuery(String sql, SqlParameterSource params) {
        return namedJdbc.query(sql, params, serviceMapper.asRowMapper());
    }
}
