package ru.it.sd.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.PersonMapper;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.Person;
import ru.it.sd.util.ResourceMessages;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

	public List<Person> list(Map<String, String> filter) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder where = new StringBuilder();
		// Фильтр по пользователю
		if (Objects.nonNull(filter) && filter.containsKey("userId")) {
			params.addValue("userId", filter.get("userId"));
			where.append("per_acc_oid = :userId");
		}
		return namedJdbc.query(
				MessageFormat.format(
						SELECT_ALL_SQL,
						StringUtils.isNotBlank(where) ? where.insert(0, "WHERE ").toString() : ""),
				params,
				(RowMapper) mapper);
	}

	/**
	 * Получает информацию о человеке по его идентификатору
	 * @param id идентификатор человека
	 * @return информация о человеке
	 */
	public Person read(Long id) {
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
	 * @return информация о человеке. Вернет null, если для данного пользователя не заведена персона
	 * @throws ServiceException если найдено более одной персоны
	 */
	public Person readByUserId(Long userId) {
		if (Objects.isNull(userId)) {
			return null;
		}
		Map<String, String> filter = new HashMap<>();
		filter.put("userId", userId.toString());
		List<Person> persons = list(filter);
		if (persons.isEmpty()) {
			return null;
		}
		if (persons.size() > 1) {
			throw new ServiceException(ResourceMessages.getMessage("error.too.many.result"));
		}
		return persons.get(0);
	}
}