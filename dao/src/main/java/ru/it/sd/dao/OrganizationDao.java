package ru.it.sd.dao;

import ru.it.sd.dao.mapper.OrgnizationMapper;
import ru.it.sd.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class OrganizationDao extends AbstractDao {

	@Autowired
	private OrgnizationMapper orgnizationMapper;

	private static final String SELECT_ALL_SQL =
			"SELECT org_oid, org_name1, org_email FROM itsm_organizations";

	public List<Organization> list() {
		return namedJdbc.query(SELECT_ALL_SQL, (RowMapper) orgnizationMapper);
	}

}


