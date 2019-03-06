package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ServiceCallMapper;
import ru.it.sd.model.ServiceCall;

import java.util.List;

@Repository
public class ServiceCallDao extends AbstractEntityDao<ServiceCall> {

    private final ServiceCallMapper serviceCallMapper;

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
            "    scs.scs_solution, " +
            "    s.ser_requestor_per_oid, " +
            "    s.ser_caller_per, " +
            "    s.ser_caller_org, " +
            "    s.ser_sla_oid, " +
            "    sla.sla_srv_oid, " +
            "    sla.sla_sel_oid, " +
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
            "    scf.scf_sershorttext4, " +
            "    scf.scf_scdate1, " +
            "    scf.scf_sctext11, " +
            "    scf.scf_per1_oid, " +
            "    scf.scf_boolean10, " +
            "    scf.scf_boolean12, " +
            "    scf.scf_cod1_oid, " +
            "    scw.scw_workaround, " +
            "    se1.se1_4k1, " +
            "    s.ser_assignstatus ass_status, " +
            "    s.ser_assignpriority ass_priority, " +
            "    s.ser_ass_per_to_oid ass_person_to, " +
            "    s.ser_ass_wog_oid ass_workgroup_to\n" +
            "FROM\n" +
            "    itsm_servicecalls s " +
            "LEFT JOIN itsm_ser_solution scs ON scs.scs_ser_oid = s.ser_oid " +
            "LEFT JOIN itsm_service_level_agreements sla ON sla.sla_oid = s.ser_sla_oid " +
            "LEFT JOIN itsm_ser_information sei ON sei.sei_ser_oid = s.ser_oid " +
            "LEFT JOIN itsm_ser_custom_fields scf ON scf.scf_ser_oid = s.ser_oid " +
            "LEFT JOIN itsm_ser_workaround scw ON scw.scw_ser_oid = s.ser_oid " +
            "LEFT JOIN itsm_ser_4k1 se1 ON se1.se1_ser_oid = s.ser_oid ";

    @Autowired
    public ServiceCallDao(ServiceCallMapper serviceCallMapper) {
        this.serviceCallMapper = serviceCallMapper;
    }

    @Override
    protected StringBuilder getBaseSql() {
        return new StringBuilder(BASE_SQL);
    }

    @Override
    protected List<ServiceCall> executeQuery(String sql, SqlParameterSource params) {
        return namedJdbc.query(sql, params, serviceCallMapper.asRowMapper());
    }
}
