package com.aplana.sd.dao.mapper;

import com.aplana.sd.model.Organization;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author quadrix
 * @since 03.05.2017
 */
public class OrgnizationMapper extends EntityMapper<Organization> {
	@Override
	public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
		Organization org = new Organization();

		return null;
	}
}
