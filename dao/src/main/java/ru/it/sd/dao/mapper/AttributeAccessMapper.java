package ru.it.sd.dao.mapper;

import org.springframework.stereotype.Component;
import ru.it.sd.model.AttributeAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AttributeAccessMapper extends EntityRowMapper<AttributeAccess> {

	@Override
	public AttributeAccess mapRow(ResultSet rs, int rowNum) throws SQLException {
        AttributeAccess attributeAccess = super.mapRow(rs, rowNum);



		return attributeAccess;
	}
}

