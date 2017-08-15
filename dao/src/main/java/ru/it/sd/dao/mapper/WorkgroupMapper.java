package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.DBUtils;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.Workgroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Маппер нарядов
 */
@Component
public class WorkgroupMapper extends EntityRowMapper<Workgroup> {

	@Autowired
	private WorkgroupDao dao;

	@Override
	public Workgroup mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Workgroup workgroup = super.mapRow(rs, rowNumber);

		Long statusId = DBUtils.getLong(rs,"wog_sta_oid");
		if (Objects.nonNull(statusId)){
			EntityStatus status = EntityStatus.get(statusId);
			workgroup.setStatus(status);
		}

		Long parentId = DBUtils.getLong(rs,"wog_parent");
		if (Objects.nonNull(parentId)){
			Workgroup parent = dao.read(parentId);
			workgroup.setParent(parent);
		}


		return workgroup;
	}
}
