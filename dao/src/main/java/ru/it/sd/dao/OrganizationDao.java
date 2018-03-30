package ru.it.sd.dao;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.OrganizationMapper;
import ru.it.sd.model.Organization;

import java.util.List;

/**
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class OrganizationDao extends AbstractEntityDao<Organization> {

	private OrganizationMapper organizationMapper;

	public OrganizationDao(OrganizationMapper organizationMapper){
		this.organizationMapper = organizationMapper;
	}
	private static final String BASE_SQL =
			"SELECT " +
				"o.org_oid, " +
				"o.org_name1, " +
				"o.org_email, " +
				"o.org_poo_oid " +
			"FROM " +
				"itsm_organizations o\n ";


	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<Organization> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, organizationMapper.asRowMapper());
	}

}


