package ru.it.sd.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import ru.it.sd.dao.mapper.OrganizationMapper;
import ru.it.sd.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class OrganizationDao extends AbstractDao {

	@Autowired
	private OrganizationMapper organizationMapper;

	private static final String SELECT_ALL_SQL =
			"SELECT " +
				"org_oid, " +
				"org_name1, " +
				"org_email " +
			"FROM " +
				"itsm_organizations " +
			"{0}";

	public List<Organization> list() {
		return namedJdbc.query(MessageFormat.format(SELECT_ALL_SQL, ""), (RowMapper) organizationMapper);
	}

	/**
	 * Возвращает организацию по её идентификатору
	 * @param id идентификатор организации
	 * @return организация
	 */
	public Organization read(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		try {
			Organization organization = namedJdbc.queryForObject(
					MessageFormat.format(SELECT_ALL_SQL, " WHERE org_oid = :id"),
					params, organizationMapper);
			return organization;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}


