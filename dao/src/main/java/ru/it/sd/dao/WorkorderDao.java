package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ChangeMapper;
import ru.it.sd.model.Change;

import java.text.MessageFormat;

/**
 * Дао для работы с данными людей
 *
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class WorkorderDao extends AbstractDao {

	@Autowired
	private ChangeMapper mapper;

	/**
	 * Общий запрос для выборки информации о человеке
	 */
	private static final String SELECT_ALL_SQL =
			"SELECT \n" +
			"ch.cha_oid, ch.cha_id\n" +
			"FROM \n" +
			"itsm_changes ch\n" +
			"{0}";

	/**
	 * Возвращает изменение по его идентификатору
	 * @param id идентификатор изменения
	 * @return изменение
	 */
	public Change findOne(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		try {
			Change change = namedJdbc.queryForObject(
					MessageFormat.format(SELECT_ALL_SQL, " WHERE ch.cha_oid = :id"),
					params, mapper);
			return change;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}