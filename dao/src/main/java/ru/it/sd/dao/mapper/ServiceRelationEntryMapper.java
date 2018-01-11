package ru.it.sd.dao.mapper;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.ChangeDao;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.EntityType;
import ru.it.sd.model.ServiceRelationEntry;
import ru.it.sd.model.ServiceRelationType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер взаимосвязей
 */
@Component
public class ServiceRelationEntryMapper extends EntityRowMapper<ServiceRelationEntry> {


    private final CodeDao codeDao;

    private final ChangeDao changeDao;

    public ServiceRelationEntryMapper(CodeDao codeDao, ChangeDao changeDao) {
        this.codeDao = codeDao;
        this.changeDao = changeDao;
    }

	@Override
	public ServiceRelationEntry mapRow(ResultSet rs, int rowNumber) throws SQLException {
        ServiceRelationEntry serviceRelationEntry = super.mapRow(rs, rowNumber);

		Long entityTypeId = DBUtils.getLong(rs,"sre_ent_oid");
		if (entityTypeId != null){
		    serviceRelationEntry.setEntityType(EntityType.get(entityTypeId));
		}

        Long typeId = DBUtils.getLong(rs,"sre_revrty_oid");
        if (typeId != null){
            BaseCode baseCode = codeDao.read(typeId);
            serviceRelationEntry.setType(baseCode.convertTo(ServiceRelationType.class));
        }

        Long changeId = DBUtils.getLong(rs,"sre_cha_oid");
        if (entityTypeId != null){
            serviceRelationEntry.setChange(changeDao.read(changeId));
        }

		return serviceRelationEntry;
	}
}
