package ru.it.sd.dao.mapper.person;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.mapper.OrganizationMapper;
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
public class PersonMapper extends EntityRowMapper<Person> {

	private final OrganizationMapper orgnizationMapper;
	private final CodeDao codeDao;

	public PersonMapper(OrganizationMapper orgnizationMapper, CodeDao codeDao){
		this.orgnizationMapper = orgnizationMapper;
		this.codeDao = codeDao;
	}
	@Override
	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
		Person person = super.mapRow(rs, rowNum);
		if (Objects.nonNull(DBUtils.getLong(rs, "per_org_oid"))) {
			Organization organization = orgnizationMapper.mapRow(rs, rowNum);
			person.setOrganization(organization);
		}

		Long folderId = DBUtils.getLong(rs, "per_poo_oid");
		if(folderId != null){
			BaseCode code = codeDao.read(folderId);
			person.setFolder(code.convertTo(Folder.class));
		}
		Long categoryId = DBUtils.getLong(rs, "per_cat_oid");
		if(categoryId != null){
			BaseCode code = codeDao.read(categoryId);
			person.setCategory(code.convertTo(EntityCategory.class));
		}
		return person;
	}
}
