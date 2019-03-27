package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.Folder;
import ru.it.sd.model.ServiceLevelAgreement;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ServiceLevelAgreementMapper extends EntityRowMapper<ServiceLevelAgreement> {

    @Autowired
    private final CodeDao codeDao;

    public ServiceLevelAgreementMapper(CodeDao codeDao) {
        this.codeDao = codeDao;
    }

    @Override
    public ServiceLevelAgreement mapRow(ResultSet rs, int rowNum) throws SQLException {
        ServiceLevelAgreement serviceLevelAgreement = super.mapRow(rs, rowNum);
        Long folderId = DBUtils.getLong(rs, "sla_pool_cod_oid");
        if(folderId != null){
            BaseCode code = codeDao.read(folderId);
            serviceLevelAgreement.setFolder(code.convertTo(Folder.class));
        }
        return serviceLevelAgreement;
    }
}
