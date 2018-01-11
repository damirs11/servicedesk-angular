package ru.it.sd.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.EntityType;
import ru.it.sd.model.FileInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Маппер информации о вложениях
 *
 * @author quadrix
 * @since 03.05.2017
 */
@Component
public class FileInfoExtractor extends EntityRowMapper<FileInfo> {

	@Override
	public List<FileInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<FileInfo> list = new ArrayList<>();

		while(rs.next()){
            FileInfo fileInfo = super.mapRow(rs, 0);
			Long entityTypeId = DBUtils.getLong(rs, "ahs_ent_oid");
			if (entityTypeId != null) {
				EntityType entityType = EntityType.get(entityTypeId);
				fileInfo.setEntityType(entityType);
			}
			list.add(fileInfo);
		}
		return list;
	}

}