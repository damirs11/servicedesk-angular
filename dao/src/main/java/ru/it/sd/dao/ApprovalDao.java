package ru.it.sd.dao;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ApprovalExtractor;
import ru.it.sd.model.Approval;
import ru.it.sd.model.Change;
import ru.it.sd.model.Workorder;

import java.util.List;

/**
 * Дао для работы с согласованием
 *
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class ApprovalDao extends AbstractDao{

	private ApprovalExtractor extractor;

    public ApprovalDao(ApprovalExtractor extractor) {
        this.extractor = extractor;
    }

	private static final String BASE_SQL =
                            "SELECT " +
                            "  cha_oid AS id, " +
							"  cha_initiator_per_oid AS initiator, " +
                            "  cha_apt_status AS apt_status, " +
                            "  cha_apt_description AS apt_description, " +
                            "  cha_apt_deadline AS deadline, " +
                            "  cha_apt_nrofapprovers AS nrofapprovers, " +
                            "  cha_apt_nrofapproversapproved AS nrofapproversapproved, " +
                            "  cha_apt_nrofapproversrequired AS nrofapproversrequired, " +
                            "  cha_apt_wog_oid AS wog_oid, " +
							"  '" + Change.class.getSimpleName() + "' AS entityType " +
                            "FROM ITSM_CHANGES " +
                            "WHERE cha_oid = :id \n " +
                            "UNION ALL " +
                            "SELECT " +
                            "  wor_oid AS id, " +
							"  wor_initiator_per_oid AS initiator, " +
                            "  wor_apt_status AS apt_status, " +
                            "  wor_apt_description AS apt_description, " +
                            "  wor_apt_deadline AS deadline, " +
                            "  wor_apt_nrofapprovers AS nrofapprovers, " +
                            "  wor_apt_nrofapproversapproved AS nrofapproversapproved, " +
                            "  wor_apt_nrofapproversrequired AS nrofapproversrequired, " +
                            "  wor_apt_wog_oid AS wog_oid, " +
							"  '" + Workorder.class.getSimpleName() + "' AS entityType " +
                            "FROM itsm_workorders " +
                            "WHERE wor_oid = :id ";

	private StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	private List<Approval> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (ResultSetExtractor<List<Approval>>) extractor);
	}

	public Approval read(Long id){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return executeQuery(getBaseSql().toString(), params).get(0);
    }
}