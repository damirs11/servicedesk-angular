package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.ChangeDao;
import ru.it.sd.dao.DBUtils;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер нарядов
 */
@Component
public class WorkgroupMapper extends EntityRowMapper<Workgroup> {


	@Override
	public Workgroup mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Workgroup workgroup = super.mapRow(rs, rowNumber);

		Long statusId = DBUtils.getLong(rs,"wog_sta_oid");
		if (statusId != null){
			EntityStatus status = EntityStatus.get(statusId);
			workgroup.setStatus(status);
		}


		return workgroup;
	}
}
