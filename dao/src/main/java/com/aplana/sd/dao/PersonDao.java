package com.aplana.sd.dao;

import com.aplana.sd.dao.mapper.PersonMapper;
import com.aplana.sd.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
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
public class PersonDao extends AbstractDao {

	@Autowired
	private PersonMapper mapper;

	/**
	 * Общий запрос для выборки информации о человеке
	 */
	private static final String SELECT_ALL_SQL =
			"SELECT \n" +
			"per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid,\n" +
			"org_oid, org_name1, org_email \n" +
			"FROM \n" +
			"itsm_persons p\n" +
			"LEFT JOIN itsm_organizations o ON o.org_oid = p.per_org_oid {0}";

	public List<Person> findAll() {
		return namedJdbc.query(
				MessageFormat.format(SELECT_ALL_SQL, ""), (RowMapper) mapper);
	}

	/**
	 * Получает информацию о человеке по его идентификатору
	 * @param id идентификатор человека
	 * @return информация о человеке
	 */
	public Person findOne(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("personId", id);
		try {
			Person person = namedJdbc.queryForObject(
					MessageFormat.format(SELECT_ALL_SQL, " WHERE per_oid = :personId"),
					params, mapper);
			return person;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * Ищет для пользователя связанную с ним информацию о человеке
	 * @param userId идентификатор пользователя
	 * @return информация о человеке
	 */
	public Person findByUserId(Long userId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userId", userId);
		try {
			Person person = namedJdbc.queryForObject(
					MessageFormat.format(SELECT_ALL_SQL, " WHERE per_acc_oid = :userId"),
					params, mapper);
			return person;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}