package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.Folder;
import ru.it.sd.model.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ServiceMapper extends EntityRowMapper<Service> {

    @Autowired
    private final CodeDao codeDao;

    public ServiceMapper(CodeDao codeDao) {
        this.codeDao = codeDao;
    }

    @Override
    public Service mapRow(ResultSet rs, int rowNum) throws SQLException {
        Service service = super.mapRow(rs, rowNum);
        if (service == null) return null;
        Long folderId = DBUtils.getLong(rs, "srv_pool_cod_oid");
        if(folderId != null){
            BaseCode code = codeDao.read(folderId);
            service.setFolder(code.convertTo(Folder.class));
        }
        Long statusId = DBUtils.getLong(rs, "srv_status_cod_oid");
        if(statusId != null){
            BaseCode code = codeDao.read(statusId);
            service.setStatus(code.convertTo(EntityStatus.class));
        }
        return service;
    }
}
