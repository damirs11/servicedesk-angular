package ru.it.sd.dao;

import com.jcabi.aspects.Cacheable;
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
import java.util.concurrent.TimeUnit;

/**
 * Дао для работы с данными людей
 *
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class PersonDao extends AbstractEntityDao {

	@Autowired
	private PersonMapper mapper;

	/**
	 * <p>Общий запрос для выборки информации о человеке.</p>
	 *
	 * <p>Два параметра:</p>
	 * <ul>
	 *     <li>1. условия для пейджинга (сортировки) вида ROW_NUMBER() OVER ...</li>
	 *     <li>2. условия для отбора записей по фильтру</li>
	 * </ul>
	 */
	private static final String BASE_SQL =
			"SELECT\n" +
			"per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid,\n" +
			"org_oid, org_name1, org_email\n" +
			"FROM\n" +
			"itsm_persons p\n" +
			"LEFT JOIN itsm_organizations o ON o.org_oid = p.per_org_oid\n";

	private static final String COUNT_SQL =
			"SELECT COUNT(*) FROM (\n" + BASE_SQL + ") t";

	public List<Person> list(Map<String, String> filter) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder sql = new StringBuilder(BASE_SQL);
		buildWhere(filter, sql, params);
		buildOrderBy(filter, sql);
		buildPaging(filter, sql, params);
		return namedJdbc.query(sql.toString(), params, (RowMapper) mapper);
	}

	public int count(Map<String, String> filter) {
		Map<String, String> filterCount = prepareFilterForCount(filter);

		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder where = new StringBuilder();
		buildWhere(filterCount, where, params);
		return namedJdbc.queryForObject(
				MessageFormat.format(COUNT_SQL, where),
				params, int.class);
	}

	private void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		FilterUtils.createFilter(sql, params, filter, Person.class);

		// Фильтр по пользователю
		if (Objects.nonNull(filter) && filter.containsKey("userId")) {
			params.addValue("userId", filter.get("userId"));
			sql.append(" AND per_acc_oid = :userId");
		}
		// todo здесь будут добавлены другие условия фильтрации.
	}

	protected void buildOrderBy(Map<String, String> filter, StringBuilder sql) {
		buildOrderBy(filter, sql, Person.class);
		// todo здесь будут добавлены другие условия сортировки.
	}

	/**
	 * Получает информацию о человеке по его идентификатору
	 * @param id идентификатор человека
	 * @return информация о человеке
	 */
	@Cacheable(lifetime = 5, unit = TimeUnit.SECONDS)
	public Person read(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("personId", id);
		try {
			Person person = namedJdbc.queryForObject(
					BASE_SQL + " WHERE per_oid = :personId",
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