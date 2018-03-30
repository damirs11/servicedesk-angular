package ru.it.sd.dao.mapper;

import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.Folder;
import ru.it.sd.model.Organization;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер для организации
 *
 * @author quadrix
 * @since 03.05.2017
 */
@Component
public class OrganizationMapper extends EntityRowMapper<Organization> {

    private final CodeDao codeDao;

    public OrganizationMapper(CodeDao codeDao){
        this.codeDao = codeDao;
    }

    @Override
    public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
        Organization organization = super.mapRow(rs, rowNum);

        Long folderId = DBUtils.getLong(rs, "org_poo_oid");
        if(folderId != null){
            BaseCode code = codeDao.read(folderId);
            organization.setFolder(code.convertTo(Folder.class));
        }

        return organization;
    }
}