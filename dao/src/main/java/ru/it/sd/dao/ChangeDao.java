package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ChangeExtractor;
import ru.it.sd.model.Change;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * Дао для работы с изменениями
 *
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class ChangeDao extends AbstractEntityDao {

	@Autowired
	private ChangeExtractor extractor;

	/**
	 * Общий запрос для выборки информации о человеке
	 */
	private static final String LIST_SQL =
			"SELECT\n" +
			"ch.cha_oid, ch.cha_id, ch.cha_description, ch.cha_requestor_per_oid,\n" +
			"ch.cha_per_man_oid, ci.chi_information, ch.cha_sta_oid, ch.cha_imp_oid,\n" +
			"ch.reg_created, ch.cha_deadline, cha_actualfinish\n" +
			"FROM\n" +
			"itsm_changes ch\n" +
			"LEFT JOIN itsm_cha_information ci ON ci.chi_cha_oid = ch.cha_oid\n" +
			"{0}";

	private static final String COUNT_SQL =
			"SELECT COUNT(*) FROM (" + LIST_SQL + ") t";

	public List<Change> list(Map<String, String> filter) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder where = new StringBuilder();
		createChangeFilter(filter, where, params);
		return namedJdbc.query(
				MessageFormat.format(LIST_SQL, where),
				params, extractor);
	}

	public int count(Map<String, String> filter) {
		Map<String, String> filterCount = prepareFilterForCount(filter);

		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder where = new StringBuilder();
		createChangeFilter(filterCount, where, params);
		return namedJdbc.queryForObject(
				MessageFormat.format(COUNT_SQL, where),
				params, int.class);
	}

	private void createChangeFilter(Map<String, String> filter, StringBuilder queryPart, MapSqlParameterSource params) {
		FilterUtils.createFilter(queryPart, params, filter, Change.class);
		// todo здесь будут добавлены другие условия фильтрации.
	}

	/**
	 * Возвращает изменение по его идентификатору
	 * @param id идентификатор изменения
	 * @return изменение
	 */
	public Change read(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		List<Change> changes = namedJdbc.query(
				MessageFormat.format(LIST_SQL, " WHERE ch.cha_oid = :id"),
				params, extractor);
		return changes.isEmpty() ? null : changes.get(0);
	}
}