package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.mapper.servicecall.ServiceCallListMapper;
import ru.it.sd.dao.mapper.servicecall.ServiceCallMapper;
import ru.it.sd.dao.mapper.servicecall.ServiceCallSimpleMapper;
import ru.it.sd.dao.utils.AccessFilterEntity;
import ru.it.sd.dao.utils.FilterMap;
import ru.it.sd.model.ServiceCall;

import java.util.List;
import java.util.Map;

@Repository
public class ServiceCallDao extends AbstractEntityDao<ServiceCall> {

    private final ServiceCallMapper serviceCallMapper;
    private final ServiceCallListMapper serviceCallListMapper;
    private final ServiceCallSimpleMapper serviceCallSimpleMapper;

    private static final String BASE_SQL =
            "SELECT\n" +
            "    s.ser_oid, " +
            "    s.ser_id, " +
            "    s.ser_poo_oid, " +
            "    s.ser_sta_oid, " +
            "    s.ser_cat_oid, " +
            "    s.ser_cla_oid, " +
            "    s.ser_clo_oid, " +
            "    s.ser_tem_oid, " +
            "    s.ser_med_oid, " +
            "    scs.scs_solution, " +
            "    s.ser_requestor_per_oid, " +
            "    s.ser_caller_per, " +
            "    s.ser_caller_org, " +
            "    s.ser_sla_oid, " +
            "    s.ser_srv_oid, " +
            "    scf.scf_sershorttext10, " +
            "    s.ser_cit_oid, " +
            "    s.ser_description, " +
            "    sei.sei_information, " +
            "    s.ser_imp_oid, " +
            "    s.ser_pri_oid, " +
            "    s.ser_deadline, " +
            "    s.ser_actualfinish, " +
            "    s.ser_latefinish, " +
            "    scf.scf_boolean1, " +
            "    scf.scf_boolean15, " +
            "    scf.scf_sershorttext4, " +
            "    scf.scf_scdate1, " +
            "    scf.scf_scdate5, " +
            "    scf.scf_scdate8, " +
            "    scf.scf_sctext11, " +
            "    scf.scf_sctext5, " +
            "    scf.scf_per1_oid, " +
            "    scf.scf_boolean10, " +
            "    scf.scf_boolean12, " +
            "    scf.scf_cod1_oid, " +
            "    scf.scf_cod7_oid, " +
            "    scf.scf_cod2_oid, " +
            "    scf.scf_cod3_oid, " +
            "    scf.scf_cod4_oid, " +
            "    scf.scf_cod5_oid, " +
            "    scf.scf_duration1, " +
            "    scw.scw_workaround, " +
            "    se1.se1_4k1, " +
            "    se2.se2_4k2, " +
            "    s.ser_frequentlyaskedquestion, " +
            "    s.ser_faq_oid, " +
            "    s.ser_assignstatus ass_status, " +
            "    s.ser_assignpriority ass_priority, " +
            "    s.ser_ass_per_to_oid ass_person_to, " +
            "    s.ser_ass_wog_oid ass_workgroup_to\n" +
            "FROM\n" +
            "    itsm_servicecalls s " +
            "LEFT JOIN itsm_ser_solution scs ON scs.scs_ser_oid = s.ser_oid " +
            "LEFT JOIN itsm_ser_information sei ON sei.sei_ser_oid = s.ser_oid " +
            "LEFT JOIN itsm_ser_custom_fields scf ON scf.scf_ser_oid = s.ser_oid " +
            "LEFT JOIN itsm_ser_workaround scw ON scw.scw_ser_oid = s.ser_oid " +
            "LEFT JOIN itsm_ser_4k1 se1 ON se1.se1_ser_oid = s.ser_oid " +
            "LEFT JOIN itsm_ser_4k2 se2 ON se2.se2_ser_oid = s.ser_oid ";

    @Autowired
    public ServiceCallDao(ServiceCallMapper serviceCallMapper, ServiceCallListMapper serviceCallListMapper, ServiceCallSimpleMapper serviceCallSimpleMapper) {
        this.serviceCallMapper = serviceCallMapper;
        this.serviceCallListMapper = serviceCallListMapper;
        this.serviceCallSimpleMapper = serviceCallSimpleMapper;
    }

    @Override
    protected StringBuilder getBaseSql() {
        return new StringBuilder(BASE_SQL);
    }

    @Override
    protected List<ServiceCall> executeQuery(String sql, SqlParameterSource params) {
        return namedJdbc.query(sql, params, serviceCallMapper.asRowMapper());
    }

    @Override
    protected List<ServiceCall> executeQuery(String sql, SqlParameterSource params, MapperMode mode) {
        EntityRowMapper<ServiceCall> entityRowMapper;
        switch (mode) {
            case SIMPLEST:
                entityRowMapper = serviceCallSimpleMapper;
                break;
            case LIST:
                entityRowMapper = serviceCallListMapper;
                break;
            case FULL:
            default:
                entityRowMapper = serviceCallMapper;
                break;
        }
        return namedJdbc.query(sql, params, entityRowMapper.asRowsExtractor());
    }

    @Override
    protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
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
                            sql.append(" AND s.ser_poo_oid in (").append(filterEntity.getFoldersString()).append(")");
                        }
                        if (filterEntity.getExecutor() != null) {
                            sql.append(" AND s.ser_ass_per_to_oid = ").append(filterEntity.getExecutor().toString()).append(" ");
                        }
                        if (!filterEntity.getWorkgroups().isEmpty()) {
                            sql.append(" AND s.ser_ass_wog_oid in (").append(filterEntity.getWorkgroupsString()).append(") ");
                        }
                        sql.append(" ) ");
                        isFirst = false;
                    }
                }
                sql.append(" ) ");
            }
        }
    }
}
