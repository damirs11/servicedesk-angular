package ru.it.sd.dao.mapper.workgroup;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.Folder;
import ru.it.sd.model.Person;
import ru.it.sd.model.Workgroup;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер нарядов
 */
@Component
public class WorkgroupMapper extends EntityRowMapper<Workgroup> {

    private final WorkgroupDao dao;
    private final CodeDao codeDao;
    private final PersonDao personDao;

    public WorkgroupMapper(@Lazy WorkgroupDao workgroupDao, CodeDao codeDao, PersonDao personDao) {
        this.codeDao = codeDao;
        this.dao = workgroupDao;
        this.personDao = personDao;
    }

    @Override
    public Workgroup mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Workgroup workgroup = super.mapRow(rs, rowNumber);
        if (workgroup == null) return null;
        Long statusId = DBUtils.getLong(rs, "wog_sta_oid");
        if (statusId != null) {
            BaseCode code = codeDao.read(statusId);
            workgroup.setStatus(code.convertTo(EntityStatus.class));
        }

        Long parentId = DBUtils.getLong(rs, "wog_parent");
        if (parentId != null) {
            Workgroup parent = dao.read(parentId);
            workgroup.setParent(parent);
        }

        Long folderId = DBUtils.getLong(rs, "wog_poo_oid");
        if (folderId != null) {
            BaseCode code = codeDao.read(folderId);
            workgroup.setFolder(code.convertTo(Folder.class));
        }
        Long groupManagerId = DBUtils.getLong(rs, "wgc_per1_oid");
        if (groupManagerId != null) {
            Person person = personDao.read(groupManagerId);
            workgroup.setGroupManager(person);
        }
        return workgroup;
    }
}
