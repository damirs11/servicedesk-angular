package ru.it.sd.dao.mapper.sla;

import org.springframework.stereotype.Component;
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
public class ServiceLevelAgreementSimpleMapper extends EntityRowMapper<ServiceLevelAgreement> {

    @Override
    public ServiceLevelAgreement mapRow(ResultSet rs, int rowNum) throws SQLException {
        ServiceLevelAgreement serviceLevelAgreement = super.mapRow(rs, rowNum);
        Long folderId = DBUtils.getLong(rs, "sla_pool_cod_oid");
        if (folderId != null) {
            BaseCode code = new BaseCode(folderId);
            serviceLevelAgreement.setFolder(code.convertTo(Folder.class));
        }
        Long statusId = DBUtils.getLong(rs, "sla_status_cod_oid");
        if (statusId != null) {
            BaseCode code = new BaseCode(statusId);
            serviceLevelAgreement.setStatus(code.convertTo(EntityStatus.class));
        }
        Long serviceId = DBUtils.getLong(rs, "sla_srv_oid");
        if (serviceId != null) {
            Service service = new Service(serviceId);
            serviceLevelAgreement.setService(service);
        }
        return serviceLevelAgreement;
    }
}
