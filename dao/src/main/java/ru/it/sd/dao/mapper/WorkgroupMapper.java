package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.DBUtils;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.model.BaseCode;
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

	private final WorkgroupDao dao;
	private final CodeDao codeDao;

	@Autowired
	public WorkgroupMapper(WorkgroupDao dao, CodeDao codeDao) {
		this.dao = dao;
		this.codeDao = codeDao;
	}

	@Override
	public Workgroup mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Workgroup workgroup = super.mapRow(rs, rowNumber);

		Long statusId = DBUtils.getLong(rs,"wog_sta_oid");
		if (statusId != null){
			BaseCode code = codeDao.read(statusId);
			workgroup.setStatus(code.convertTo(EntityStatus.class));
		}

		Long parentId = DBUtils.getLong(rs,"wog_parent");
		if (parentId != null){
			Workgroup parent = dao.read(parentId);
			workgroup.setParent(parent);
		}

		return workgroup;
	}
}
