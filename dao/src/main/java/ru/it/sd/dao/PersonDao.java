package ru.it.sd.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.mapper.person.PersonListMapper;
import ru.it.sd.dao.mapper.person.PersonMapper;
import ru.it.sd.dao.mapper.person.PersonSimpleMapper;
import ru.it.sd.dao.utils.AccessFilterEntity;
import ru.it.sd.dao.utils.FilterMap;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.Person;
import ru.it.sd.util.ResourceMessages;

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
public class PersonDao extends AbstractEntityDao<Person> {

	private final PersonMapper mapper;
	private final PersonListMapper personListMapper;
	private final PersonSimpleMapper personSimpleMapper;

	public PersonDao(PersonMapper mapper, PersonListMapper personListMapper, PersonSimpleMapper personSimpleMapper){
		this.mapper = mapper;
		this.personListMapper = personListMapper;
		this.personSimpleMapper = personSimpleMapper;
	}

	private static final String BASE_SQL =
			"SELECT\n" +
			"	p.per_oid, " +
			"	p.per_gender, " +
			"	p.per_email, " +
			"	p.per_jobtitle, " +
			"	p.per_firstname, " +
			"	p.per_lastname, " +
			"	p.per_middlename, " +
            "   p.per_name, " +
            "	o.org_name1, " +
            "	o.org_oid, " +
            "	o.org_email, " +
            "	o.org_poo_oid, " +
			"	p.per_org_oid, " +
			"	p.per_cat_oid, " +
			"	p.per_notselectable, " +
			"	p.per_poo_oid\n " +
			"FROM\n" +
			"itsm_persons p\n" +
            "LEFT JOIN itsm_organizations o ON p.per_org_oid = org_oid";

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<Person> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, mapper.asRowMapper());
	}

	@Override
	protected List<Person> executeQuery(String sql, SqlParameterSource params, MapperMode mode) {
		EntityRowMapper<Person> entityRowMapper;
		switch (mode) {
			case SIMPLEST:
				entityRowMapper = personSimpleMapper;
				break;
			case LIST:
				entityRowMapper = personListMapper;
				break;
			case FULL:
			default:
				entityRowMapper = mapper;
				break;
		}
		return namedJdbc.query(sql, params, entityRowMapper.asRowMapper());
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		super.buildWhere(filter, sql, params);

		if (Objects.nonNull(filter) && filter.containsKey("selectable")) {
			params.addValue("selectable", filter.get("selectable").equals("0") ? 1 : 0);//Инверсия non-selectable->selectable
			sql.append(" AND per_notselectable = :selectable");
		}

		if (Objects.nonNull(filter) && filter.containsKey("hasAccount")) {
			sql.append(" AND per_acc_oid is not null");
		}

		// Фильтр по пользователю
		if (Objects.nonNull(filter) && filter.containsKey("userId")) {
			params.addValue("userId", filter.get("userId"));
			sql.append(" AND per_acc_oid = :userId");
		}

		// Фильтр по рабочей группе
		if (Objects.nonNull(filter) && filter.containsKey("workgroupId")) {
			params.addValue("workgroupId", filter.get("workgroupId"));
			sql.append(" AND p.per_oid IN (" +
					"SELECT mem_per_oid FROM itsm_members " +
					"WHERE mem_wog_oid = :workgroupId)");
		}
		if (filter instanceof FilterMap) {
			List<AccessFilterEntity> accessFilter = ((FilterMap) filter).getAccessFilter();
			if (!accessFilter.isEmpty()) {
				sql.append(" AND ( ");
				if (accessFilter.size() == 1 && accessFilter.get(0).getNoAccess()) {
					sql.append("1=0");
				} else {
					boolean isFirst = true;
					for (AccessFilterEntity filterEntity : accessFilter) {
						sql.append(isFirst ? " ( 1=1 " : " OR ( 1=1");
						if (!filterEntity.getFolders().isEmpty()) {
							sql.append(" AND p.per_poo_oid in (").append(filterEntity.getFoldersString()).append(")");
						}
						sql.append(" ) ");
						isFirst = false;
					}
				}
				sql.append(" ) ");
			}
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