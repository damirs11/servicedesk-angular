package ru.it.sd.dao.mapper;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.GrantDao;
import ru.it.sd.dao.RoleDao;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.AttributeAccess;
import ru.it.sd.model.Grant;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AttributeAccessMapper extends EntityRowMapper<AttributeAccess> {

	private GrantDao grantDao;

	public AttributeAccessMapper(GrantDao grantDao) {
		this.grantDao = grantDao;
	}

	@Override
	public AttributeAccess mapRow(ResultSet rs, int rowNum) throws SQLException {
        AttributeAccess attributeAccess = super.mapRow(rs, rowNum);

        Long grantId = DBUtils.getLong(rs, "ata_ena_oid");
        if(grantId != null){
            Grant grant = grantDao.read(grantId);
            attributeAccess.setGrant(grant);
        }

		return attributeAccess;
	}
}