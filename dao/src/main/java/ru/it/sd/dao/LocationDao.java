package ru.it.sd.dao;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.LocationMapper;
import ru.it.sd.model.Location;

import java.util.List;

@Repository
public class LocationDao extends AbstractEntityDao<Location> {

	private LocationMapper mapper;

	public LocationDao(LocationMapper mapper){
		this.mapper = mapper;
	}
	private static final String BASE_SQL =
			"SELECT " +
				"LOC_OID, " +
				"LOC_SEARCHCODE " +
			"FROM " +
				"ITSM_LOCATIONS\n";


	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<Location> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, mapper.asRowMapper());
	}
}


