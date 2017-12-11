package ru.it.sd.dao.mapper;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер согласований
 */
@Component
public class ApproverVoteMapper extends EntityRowMapper<ApproverVote> {

	private PersonDao personDao;

    public ApproverVoteMapper(PersonDao personDao) {
	    this.personDao = personDao;
    }

	@Override
	public ApproverVote mapRow(ResultSet rs, int rowNumber) throws SQLException {
		ApproverVote approverVote = super.mapRow(rs, rowNumber);

		Long approverId = DBUtils.getLong(rs,"apv_per_oid");
		if (approverId != null){
			Person approver = personDao.read(approverId);
			approverVote.setApprover(approver);
		}

		return approverVote;
	}
}
