package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.*;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер нарядов
 */
@Component
public class ConfigurationItemMapper extends EntityRowMapper<ConfigurationItem> {

	private final PersonDao personDao;
	private final OrganizationDao organizationDao;
	private final LocationDao locationDao;
	private final CodeDao codeDao;

	@Autowired
	public ConfigurationItemMapper(PersonDao personDao, OrganizationDao organizationDao, LocationDao locationDao, CodeDao codeDao) {
		this.personDao = personDao;
		this.organizationDao = organizationDao;
		this.locationDao = locationDao;
		this.codeDao = codeDao;
	}

	@Override
	public ConfigurationItem mapRow(ResultSet rs, int rowNumber) throws SQLException {
		ConfigurationItem item = super.mapRow(rs, rowNumber);
		Long statusId = DBUtils.getLong(rs,"cit_sta_oid");
		if (statusId != null) {
            BaseCode code = codeDao.read(statusId);
			item.setStatus(code.convertTo(EntityStatus.class));
		}
		Long categoryId = DBUtils.getLong(rs,"cit_cat_oid");
		if (categoryId != null) {
            BaseCode code = codeDao.read(categoryId);
			EntityCategory category = code.convertTo(EntityCategory.class);
			item.setCategory(category);
		}
		Long locationId = DBUtils.getLong(rs,"cit_loc_oid");
		if (locationId != null) {
			Location location = locationDao.read(locationId);
			item.setLocation(location);
		}
		Long folderId = DBUtils.getLong(rs,"cit_poo_oid");
		if (folderId != null) {
			BaseCode code = codeDao.read(folderId);
			item.setFolder(code.convertTo(Folder.class));
		}
		Long brandId = DBUtils.getLong(rs,"cit_bra_oid");
		if (brandId != null) {
			BaseCode code = codeDao.read(brandId);
			item.setBrand(code.convertTo(Brand.class));
		}
		Long adminId = DBUtils.getLong(rs,"cit_admin_per_oid");
		if (adminId != null) {
			Person admin = personDao.read(adminId);
			item.setAdmin(admin);
		}
		Long ownerId = DBUtils.getLong(rs,"cit_owner_per_oid");
		if (ownerId != null) {
			Person owner = personDao.read(ownerId);
			item.setOwner(owner);
		}
		Long ownerOrgId = DBUtils.getLong(rs,"cit_owner_org_oid");
		if (ownerOrgId != null) {
			Organization ownerOrg = organizationDao.read(ownerOrgId);
			item.setOwnerOrganization(ownerOrg);
		}
		Long payerId = DBUtils.getLong(rs,"ccf_org1_oid");
		if (payerId != null) {
			Organization payer = organizationDao.read(payerId);
			item.setPayer(payer);
		}
		Long supplierId = DBUtils.getLong(rs,"cit_org_oid");
		if (supplierId != null) {
			Organization supplier = organizationDao.read(supplierId);
			item.setSupplier(supplier);
		}

		return item;
	}
}
