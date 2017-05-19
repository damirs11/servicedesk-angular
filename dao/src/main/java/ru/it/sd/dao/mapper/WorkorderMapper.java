package ru.it.sd.dao.mapper;

import org.springframework.stereotype.Component;
import ru.it.sd.model.Workorder;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер нарядов
 */
@Component
public class WorkorderMapper extends EntityRowMapper<Workorder> {

	@Override
	public Workorder mapRow(ResultSet rs, int rowNum) throws SQLException {
		Workorder workorder = super.mapRow(rs, rowNum);

		return workorder;
	}
}
