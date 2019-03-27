package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ServiceLevelAgreementMapper;
import ru.it.sd.model.ServiceLevelAgreement;

import java.util.List;

@Repository
public class ServiceLevelAgreementDao extends AbstractEntityDao<ServiceLevelAgreement> {

    private final ServiceLevelAgreementMapper serviceLevelAgreementMapper;

    private static final String BASE_SQL =
            "SELECT\n" +
            "   sla.sla_oid, " +
            "   sla.sla_name, " +
            "   sla.sla_srv_oid, " +
            "   sla.sla_sel_oid, " +
            "   sla.sla_pool_cod_oid\n" +
            "FROM itsm_service_level_agreements sla";

    @Autowired
    public ServiceLevelAgreementDao(ServiceLevelAgreementMapper serviceLevelAgreementMapper) {
        this.serviceLevelAgreementMapper = serviceLevelAgreementMapper;
    }

    @Override
    protected StringBuilder getBaseSql() {
        return new StringBuilder(BASE_SQL);
    }

    @Override
    protected List<ServiceLevelAgreement> executeQuery(String sql, SqlParameterSource params) {
        return namedJdbc.query(sql, params, serviceLevelAgreementMapper.asRowMapper());
    }
}
