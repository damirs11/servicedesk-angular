package com.aplana.sd.dao;

import com.aplana.sd.dao.mapper.OrgnizationMapper;
import com.aplana.sd.model.Organization;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class OrganizationDao extends AbstractDao {

	public List<Organization> findAll() {
		return namedJdbc.query("SELECT org_oid, org_name1, org_email FROM itsm_organizations", (RowMapper) new OrgnizationMapper());
	}

}


