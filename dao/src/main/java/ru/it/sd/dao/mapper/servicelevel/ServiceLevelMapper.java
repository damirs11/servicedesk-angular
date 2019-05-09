package ru.it.sd.dao.mapper.servicelevel;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.ServiceLevelPriorityImpactSettingDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.model.ServiceLevel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Маппер условий предоставления
 *
 * @author nsychev
 */
@Component
public class ServiceLevelMapper extends EntityRowMapper<ServiceLevel> {


    private final ServiceLevelPriorityImpactSettingDao serviceLevelPriorityImpactSettingDao;

    public ServiceLevelMapper(ServiceLevelPriorityImpactSettingDao serviceLevelPriorityImpactSettingDao) {
        this.serviceLevelPriorityImpactSettingDao = serviceLevelPriorityImpactSettingDao;
    }

    @Override
    public ServiceLevel mapRow(ResultSet rs, int rowNum) throws SQLException {
        ServiceLevel serviceLevel = super.mapRow(rs, rowNum);
        if (serviceLevel == null) return null;
        Map<String, String> filter = new HashMap<>();
        filter.put("serviceLevelId", serviceLevel.getId().toString());
        serviceLevel.getImpactSettingList().addAll(serviceLevelPriorityImpactSettingDao.list(filter));
        return serviceLevel;
    }
}
