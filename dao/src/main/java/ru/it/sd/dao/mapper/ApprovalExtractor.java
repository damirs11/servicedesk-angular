package ru.it.sd.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.DBUtils;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.model.Approval;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.Workgroup;

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

	private WorkgroupDao workgroupDao;

	public ApprovalExtractor(WorkgroupDao workgroupDao) {
		this.workgroupDao = workgroupDao;
	}

	@Override
	public List<Approval> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<Approval> list = new ArrayList<>();

		while(rs.next()){
			Approval approval = super.mapRow(rs, 0);

			Long statusId = DBUtils.getLong(rs, "apt_status");
			if(statusId !=null) approval.setStatus(EntityStatus.get(statusId));

			Long workgroupID = DBUtils.getLong(rs, "wog_oid");
			if(workgroupID != null) {
			    Workgroup workgroup = workgroupDao.read(workgroupID);
			    approval.setApprovalWorkgroup(workgroup);
            }

			list.add(approval);
		}
		return list;
	}

}