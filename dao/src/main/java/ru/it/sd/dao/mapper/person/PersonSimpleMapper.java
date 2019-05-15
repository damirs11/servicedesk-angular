package ru.it.sd.dao.mapper.person;

import org.springframework.stereotype.Component;
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
public class PersonSimpleMapper extends EntityRowMapper<Person> {

	@Override
	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
		Person person = super.mapRow(rs, rowNum);
		if (Objects.nonNull(DBUtils.getLong(rs, "per_org_oid"))) {
			person.setOrganization(new Organization(DBUtils.getLong(rs, "per_org_oid")));
		}

		Long folderId = DBUtils.getLong(rs, "per_poo_oid");
		if(folderId != null){
			BaseCode code = new BaseCode(folderId);
			person.setFolder(code.convertTo(Folder.class));
		}
		Long categoryId = DBUtils.getLong(rs, "per_cat_oid");
		if(categoryId != null){
			BaseCode code = new BaseCode(categoryId);
			person.setCategory(code.convertTo(EntityCategory.class));
		}
		return person;
	}
}
