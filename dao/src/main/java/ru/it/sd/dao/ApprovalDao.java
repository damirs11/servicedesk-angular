package ru.it.sd.dao;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ApprovalExtractor;
import ru.it.sd.model.Approval;

import java.util.List;

/**
 * Дао для работы с изменениями
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
                            "SELECT  " +
                            "  cha_oid as id,  " +
                            "  cha_apt_status as apt_status,  " +
                            "  cha_apt_description as apt_description,  " +
                            "  cha_apt_deadline as deadline,  " +
                            "  cha_apt_nrofapprovers as nrofapprovers,  " +
                            "  cha_apt_nrofapproversapproved as nrofapproversapproved,  " +
                            "  cha_apt_nrofapproversrequired as nrofapproversrequired,  " +
                            "  cha_apt_wog_oid as wog_oid " +
                            "FROM ITSM_CHANGES  " +
                            "WHERE cha_oid =  :id \n " +
                            "UNION ALL " +
                            "SELECT  " +
                            "  wor_oid as id,  " +
                            "  wor_apt_status as apt_status,  " +
                            "  wor_apt_description as apt_description,  " +
                            "  wor_apt_deadline as deadline,  " +
                            "  wor_apt_nrofapprovers as nrofapprovers,  " +
                            "  wor_apt_nrofapproversapproved as nrofapproversapproved,  " +
                            "  wor_apt_nrofapproversrequired as nrofapproversrequired,  " +
                            "  wor_apt_wog_oid as wog_oid " +
                            "FROM itsm_workorders  " +
                            "WHERE wor_oid =  :id ";

	private StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	private List<Approval> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (ResultSetExtractor<List<Approval>>) extractor);
	}

	public Approval read(Long id){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        StringBuilder sql = getBaseSql();
        return executeQuery(BASE_SQL, params).get(0);
    }
}