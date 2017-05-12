package com.aplana.sd.dao.mapper;

import com.aplana.sd.dao.DBUtils;
import com.aplana.sd.model.Change;
import com.aplana.sd.model.Organization;
import com.aplana.sd.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

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
