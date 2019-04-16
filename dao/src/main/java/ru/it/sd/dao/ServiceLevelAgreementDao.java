package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ServiceLevelAgreementMapper;
import ru.it.sd.model.ServiceLevelAgreement;

import java.util.List;
import java.util.Map;

@Repository
public class ServiceLevelAgreementDao extends AbstractEntityDao<ServiceLevelAgreement> {

    private final ServiceLevelAgreementMapper serviceLevelAgreementMapper;

    private static final String BASE_SQL =
            "SELECT DISTINCT\n" +
            "   sla.sla_oid, " +
            "   sla.sla_name, " +
            "   sla.sla_srv_oid, " +
            "   sla.sla_actualstart, " +
            "   sla.sla_actualfinish, " +
            "   sla.sla_status_cod_oid, " +
            "   sla.sla_sel_oid, " +
            "   sla.sla_pool_cod_oid\n" +
            "FROM itsm_service_level_agreements sla\n" +
            "LEFT JOIN itsm_sc_rec_organizations sro ON sro.sro_sla_oid = sla.sla_oid\n" +
            "LEFT JOIN itsm_sc_rec_persons srp ON srp.srp_sla_oid = sla.sla_oid\n";

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

    @Override
    protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
        super.buildWhere(filter, sql, params);
        if (filter == null || filter.isEmpty()) return;
        if (filter.containsKey("organizationId")) {
            params.addValue("organizationId", filter.get("organizationId"));
            sql.append(" AND (sro.sro_org_oid in (select id from SdGetOrganizations(:organizationId, 1)) ")
                    .append("OR sla.sla_pro_org_oid in (select id from SdGetOrganizations(:organizationId, 1) ) ");
            if (filter.containsKey("personId")) {
                params.addValue("personId", filter.get("personId"));
                sql.append(" OR srp.srp_per_oid = :personId");
            }
            sql.append(" ) ");
        }
    }
}