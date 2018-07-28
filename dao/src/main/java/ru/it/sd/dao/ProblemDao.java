package ru.it.sd.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ProblemMapper;
import ru.it.sd.model.Problem;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Дао для работы с "Проблемами"
 *
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class ProblemDao extends AbstractEntityDao<Problem> {

	private ProblemMapper problemMapper;

	private static final String BASE_SQL =
			"SELECT \n" +
				"pro_oid, \n" +
				"pro_id, \n" +
				"pro_sta_oid, \n" +
				"pro_requestor_per_oid, \n" +
				"pro_cit_oid, \n" +
				"pro_description, \n" +
				"pri.pri_information, \n" +
				"pcf.pcf_problemtext4, \n" +
				"pcf.pcf_problemtext1, \n" +
				"pcf.pcf_problemtext3, \n" +
				"pr2.pr2_4k2, \n" +
				"prs.prs_solution, \n" +
				"pro_workaround, \n" +
				"pro_imp_oid, \n" +
				"pro_deadline, \n" +
				"pro_actualfinish, \n" +
				"pro_latefinish, \n" +
				"pcf.pcf_boolean12, \n" +
				"pro_planfinish, \n" +
				"pcf.pcf_problemtext2, \n" +
				"pro_cat_oid, \n" +
				"pro_cla_oid, \n" +
				"pro_clo_oid, \n" +
				"pro_poo_oid, \n" +
				"pcf.pcf_boolean1, \n" +
				"pcf.pcf_problemdate2, \n" +
				"pr1.pr1_4k1 \n" +
			"FROM itsm_problems pro \n" +
				"LEFT JOIN itsm_pro_information pri on pri.pri_pro_oid = pro.pro_oid \n" +
				"LEFT JOIN itsm_pro_custom_fields pcf ON (pcf.pcf_pro_oid = pro.pro_oid) \n" +
				"LEFT JOIN itsm_pro_4k2 pr2 ON (pr2.pr2_pro_oid = pro.pro_oid) \n" +
				"LEFT JOIN itsm_pro_4k1 pr1 ON (pr1.pr1_pro_oid = pro.pro_oid) \n" +
				"LEFT JOIN itsm_pro_solution prs ON (prs.prs_pro_oid = pro.pro_oid)";

	public ProblemDao(ProblemMapper problemMapper) {
		this.problemMapper = problemMapper;
	}

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<Problem> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (RowMapper<Problem>) problemMapper);
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		if (Objects.isNull(filter) || filter.isEmpty()) {
			return;
		}
		super.buildWhere(filter, sql, params);
	}
}