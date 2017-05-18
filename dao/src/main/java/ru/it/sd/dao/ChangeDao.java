package ru.it.sd.dao;

import ru.it.sd.dao.mapper.ChangeExtractor;
import ru.it.sd.model.Change;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.List;

/**
 * Дао для работы с данными людей
 *
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class ChangeDao extends AbstractDao {

	@Autowired
	private ChangeExtractor extractor;

	/**
	 * Общий запрос для выборки информации о человеке
	 */
	private static final String SELECT_ALL_SQL =
			"SELECT\n" +
			"ch.cha_oid, ch.cha_id, ch.cha_description, ch.cha_requestor_per_oid,\n" +
			"ch.cha_per_man_oid, ci.chi_information, ch.cha_sta_oid, ch.cha_imp_oid,\n" +
			"ch.reg_created, ch.cha_deadline, cha_actualfinish\n" +
			"FROM\n" +
			"itsm_changes ch\n" +
			"LEFT JOIN itsm_cha_information ci ON ci.chi_cha_oid = ch.cha_oid\n" +
			"{0}";

	/**
	 * Возвращает изменение по его идентификатору
	 * @param id идентификатор изменения
	 * @return изменение
	 */
	public Change findOne(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		List<Change> changes = namedJdbc.query(
				MessageFormat.format(SELECT_ALL_SQL, " WHERE ch.cha_oid = :id"),
				params, extractor);
		return changes.isEmpty() ? null : changes.get(0);
	}
}