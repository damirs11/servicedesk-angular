package com.aplana.sd.dao.mapper;

import com.aplana.sd.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author quadrix
 * @since 03.05.2017
 */
public class PersonMapper extends EntityMapper<Person> {
	@Override
	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
		return null;
	}
}
