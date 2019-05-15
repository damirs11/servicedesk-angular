package ru.it.sd.dao.mapper.person;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.OrganizationDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.EntityCategory;
import ru.it.sd.model.Folder;
import ru.it.sd.model.Organization;
import ru.it.sd.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Маппер персон
 *
 * @author quadrix
 * @since 03.05.2017
 */
@Component
public class PersonListMapper extends EntityRowMapper<Person> {

	private final OrganizationDao organizationDao;
	private final CodeDao codeDao;

	public PersonListMapper(OrganizationDao organizationDao, CodeDao codeDao){
		this.organizationDao = organizationDao;
		this.codeDao = codeDao;
	}
	@Override
	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
		Person person = super.mapRow(rs, rowNum);
		Long personId = DBUtils.getLong(rs, "per_org_oid");
		if (Objects.nonNull(personId)) {
			Organization organization = organizationDao.read(personId, AbstractEntityDao.MapperMode.SIMPLEST);
			person.setOrganization(organization);
		}

		Long folderId = DBUtils.getLong(rs, "per_poo_oid");
		if(folderId != null){
			BaseCode code = codeDao.read(folderId, AbstractEntityDao.MapperMode.SIMPLEST);
			person.setFolder(code.convertTo(Folder.class));
		}
		Long categoryId = DBUtils.getLong(rs, "per_cat_oid");
		if(categoryId != null){
			BaseCode code = codeDao.read(categoryId, AbstractEntityDao.MapperMode.SIMPLEST);
			person.setCategory(code.convertTo(EntityCategory.class));
		}
		return person;
	}
}
