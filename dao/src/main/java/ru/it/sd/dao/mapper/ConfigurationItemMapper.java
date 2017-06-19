package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.*;
import ru.it.sd.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер нарядов
 */
@Component
public class ConfigurationItemMapper extends EntityRowMapper<ConfigurationItem> {

	@Autowired
	private PersonDao personDao;
	@Autowired
	private OrganizationDao organizationDao;
	@Autowired
	private LocationDao locationDao;

	@Override
	public ConfigurationItem mapRow(ResultSet rs, int rowNumber) throws SQLException {
		ConfigurationItem item = super.mapRow(rs, rowNumber);

		Long statusId = DBUtils.getLong(rs,"CIT_STA_OID");
		if (statusId != null) {
			EntityStatus status = EntityStatus.get(statusId);
			item.setStatus(status);
		}
		Long categoryId = DBUtils.getLong(rs,"CIT_CAT_OID");
		if (categoryId != null) {
			EntityCategory category = EntityCategory.getById(categoryId);
			item.setCategory(category);
		}
		Long locationId = DBUtils.getLong(rs,"CIT_LOC_OID");
		if (locationId != null) {
			Location location = locationDao.read(locationId);
			item.setLocation(location);
		}
		Long adminId = DBUtils.getLong(rs,"CIT_ADMIN_PER_OID");
		if (adminId != null) {
			Person admin = personDao.read(adminId);
			item.setAdmin(admin);
		}
		Long ownerId = DBUtils.getLong(rs,"CIT_OWNER_PER_OID");
		if (ownerId != null) {
			Person owner = personDao.read(ownerId);
			item.setAdmin(owner);
		}
		Long ownerOrgId = DBUtils.getLong(rs,"CIT_OWNER_ORG_OID");
		if (ownerOrgId != null) {
			Organization ownerOrg = organizationDao.read(ownerOrgId);
			item.setOwnerOrganization(ownerOrg);
		}
		Long payerId = DBUtils.getLong(rs,"CCF_ORG1_OID");
		if (payerId != null) {
			Organization payer = organizationDao.read(payerId);
			item.setPayer(payer);
		}
		Long supplierId = DBUtils.getLong(rs,"CIT_ORG_OID");
		if (supplierId != null) {
			Organization supplier = organizationDao.read(supplierId);
			item.setSupplier(supplier);
		}

		return item;
	}
}
