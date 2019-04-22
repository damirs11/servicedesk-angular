package ru.it.sd.dao.mapper.service;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.Folder;
import ru.it.sd.model.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ServiceSimpleMapper extends EntityRowMapper<Service> {

    @Override
    public Service mapRow(ResultSet rs, int rowNum) throws SQLException {
        Service service = super.mapRow(rs, rowNum);
        if (service == null) return null;
        Long folderId = DBUtils.getLong(rs, "srv_pool_cod_oid");
        if (folderId != null) {
            BaseCode code = new BaseCode(folderId);
            service.setFolder(code.convertTo(Folder.class));
        }
        Long statusId = DBUtils.getLong(rs, "srv_status_cod_oid");
        if (statusId != null) {
            BaseCode code = new BaseCode(statusId);
            service.setStatus(code.convertTo(EntityStatus.class));
        }
        return service;
    }
}
