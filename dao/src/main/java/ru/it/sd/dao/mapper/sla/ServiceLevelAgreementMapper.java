package ru.it.sd.dao.mapper.sla;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.ServiceDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.Folder;
import ru.it.sd.model.Service;
import ru.it.sd.model.ServiceLevelAgreement;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ServiceLevelAgreementMapper extends EntityRowMapper<ServiceLevelAgreement> {

    private final CodeDao codeDao;
    private final ServiceDao serviceDao;

    @Autowired
    public ServiceLevelAgreementMapper(CodeDao codeDao, ServiceDao serviceDao) {
        this.codeDao = codeDao;
        this.serviceDao = serviceDao;
    }

    @Override
    public ServiceLevelAgreement mapRow(ResultSet rs, int rowNum) throws SQLException {
        ServiceLevelAgreement serviceLevelAgreement = super.mapRow(rs, rowNum);
        Long folderId = DBUtils.getLong(rs, "sla_pool_cod_oid");
        if (folderId != null) {
            BaseCode code = codeDao.read(folderId);
            serviceLevelAgreement.setFolder(code.convertTo(Folder.class));
        }
        Long statusId = DBUtils.getLong(rs, "sla_status_cod_oid");
        if (statusId != null) {
            BaseCode code = codeDao.read(statusId);
            serviceLevelAgreement.setStatus(code.convertTo(EntityStatus.class));
        }
        Long serviceId = DBUtils.getLong(rs, "sla_srv_oid");
        if (serviceId != null) {
            Service service = serviceDao.read(serviceId);
            serviceLevelAgreement.setService(service);
        }
        return serviceLevelAgreement;
    }
}
