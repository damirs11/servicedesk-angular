package ru.it.sd.dao.mapper;

import ru.it.sd.dao.DBUtils;
import ru.it.sd.model.Organization;
import ru.it.sd.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	@Autowired
	private OrganizationMapper orgnizationMapper;

	@Override
	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
		Person person = super.mapRow(rs, rowNum);
		if (Objects.nonNull(DBUtils.getLong(rs, "per_org_oid"))) {
			Organization organization = orgnizationMapper.mapRow(rs, rowNum);
			person.setOrganization(organization);
		}
		return person;
	}
}
