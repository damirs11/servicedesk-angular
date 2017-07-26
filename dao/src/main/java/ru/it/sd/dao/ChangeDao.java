package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ChangeExtractor;
import ru.it.sd.model.Change;

import java.util.List;

/**
 * Дао для работы с изменениями
 *
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class ChangeDao extends AbstractEntityDao<Change> {

	@Autowired
	private ChangeExtractor extractor;

	private static final String BASE_SQL =
			"SELECT\n" +
			"ch.cha_oid, ch.cha_id, ch.cha_description, ch.cha_requestor_per_oid,\n" +
			"ch.cha_per_man_oid, ci.chi_information, ch.cha_sta_oid, ch.cha_imp_oid,\n" +
			"ch.reg_created, ch.cha_deadline, cha_actualfinish\n" +
			"FROM\n" +
			"itsm_changes ch\n" +
			"LEFT JOIN itsm_cha_information ci ON ci.chi_cha_oid = ch.cha_oid\n";

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<Change> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, extractor);
	}
}