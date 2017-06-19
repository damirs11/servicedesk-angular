package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.LocationMapper;
import ru.it.sd.model.Location;

import java.text.MessageFormat;
import java.util.List;

@Repository
public class LocationDao extends AbstractDao {

	@Autowired
	private LocationMapper mapper;

	private static final String SELECT_ALL_SQL =
			"SELECT " +
				"LOC_OID, " +
				"LOC_SEARCHCODE, " +
			"FROM " +
				"ITSM_LOCATIONS " +
			"{0}";

	public List<Location> getAll() {
		return namedJdbc.query(MessageFormat.format(SELECT_ALL_SQL, ""), (RowMapper) mapper);
	}

	/**
	 * Возвращает местоположение по идентификатору
	 * @param id идентификатор местоположения
	 * @return местоположение
	 */
	public Location read(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		try {
			Location location = namedJdbc.queryForObject(
					MessageFormat.format(SELECT_ALL_SQL, " WHERE org_oid = :id"),
					params, mapper);
			return location;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}


