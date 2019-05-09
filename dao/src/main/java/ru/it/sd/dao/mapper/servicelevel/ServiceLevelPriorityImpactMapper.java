package ru.it.sd.dao.mapper.servicelevel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.EntityPriority;
import ru.it.sd.model.ServiceLevelPriority;
import ru.it.sd.model.ServiceLevelPriorityImpactSetting;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер настроек условия приоритета по отношению к приоритету сущности
 *
 * @author nsychev
 */
@Component
public class ServiceLevelPriorityImpactMapper extends EntityRowMapper<ServiceLevelPriorityImpactSetting> {

    @Autowired
    private CodeDao codeDao;

    @Override
    public ServiceLevelPriorityImpactSetting mapRow(ResultSet rs, int rowNum) throws SQLException {
        ServiceLevelPriorityImpactSetting serviceLevelPriorityImpactSetting = super.mapRow(rs, rowNum);
        Long serviceLevelPriorityId = DBUtils.getLong(rs, "pis_pri_oid");
        if (serviceLevelPriorityId != null) {
            BaseCode serviceLevelPriority = codeDao.read(serviceLevelPriorityId);
            serviceLevelPriorityImpactSetting.setServiceLevelPriority(serviceLevelPriority.convertTo(ServiceLevelPriority.class));
        }
        Long priorityId = DBUtils.getLong(rs, "pis_imp_oid");
        if (priorityId != null) {
            BaseCode priority = codeDao.read(priorityId);
            serviceLevelPriorityImpactSetting.setPriority(priority.convertTo(EntityPriority.class));
        }
        return serviceLevelPriorityImpactSetting;
    }
}
