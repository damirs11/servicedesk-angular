package ru.it.sd.dao.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.Approval;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.EntityType;
import ru.it.sd.model.Workgroup;
import ru.it.sd.util.EntityUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Маппер персон
 *
 * @author quadrix
 * @since 03.05.2017
 */
@Component
public class ApprovalExtractor extends EntityRowMapper<Approval> {

	private final WorkgroupDao workgroupDao;
	private final CodeDao codeDao;

	public ApprovalExtractor(WorkgroupDao workgroupDao, CodeDao codeDao) {
		this.workgroupDao = workgroupDao;
		this.codeDao = codeDao;
	}

	@Override
	public List<Approval> extractData(ResultSet rs) throws SQLException {

		List<Approval> list = new ArrayList<>();

		while(rs.next()){
			Approval approval = super.mapRow(rs, 0);

			Long statusId = DBUtils.getLong(rs, "apt_status");
			if(statusId != null) {
				BaseCode code = codeDao.read(statusId);
				approval.setStatus(code.convertTo(EntityStatus.class));
			}

			Long workgroupID = DBUtils.getLong(rs, "wog_oid");
			if(workgroupID != null) {
			    Workgroup workgroup = workgroupDao.read(workgroupID);
			    approval.setApprovalWorkgroup(workgroup);
            }

            String entityTypeStr = rs.getString("entityType");
			if (StringUtils.isNotBlank(entityTypeStr)) {
				Class entityClass = EntityUtils.getEntityClass(entityTypeStr.trim());
				approval.setEntityType(EntityType.getByClass(entityClass));
			}

			list.add(approval);
		}
		return list;
	}

}