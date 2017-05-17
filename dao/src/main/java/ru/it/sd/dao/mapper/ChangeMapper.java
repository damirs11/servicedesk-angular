package ru.it.sd.dao.mapper;

import ru.it.sd.model.Change;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер персон
 *
 * @author quadrix
 * @since 03.05.2017
 */
@Component
public class ChangeMapper extends EntityRowMapper<Change> {

	@Override
	public Change mapRow(ResultSet rs, int rowNum) throws SQLException {
		Change change = super.mapRow(rs, rowNum);

		return change;
	}
}
