package com.aplana.sd.dao;

import com.aplana.sd.model.Organization;
import org.springframework.data.repository.CrudRepository;

/**
 * @author quadrix
 *         28.04.2017 2:54
 */
public interface OrganizationDao extends CrudRepository<Organization, Long> {

	Organization findById(Long id);
	//private static final String GET_ORG = "SELECT org_oid, org_name1, org_email FROM itsm_organizations o WHERE org_oid = :id";
	//public Organization get(long id) {
}


