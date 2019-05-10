package ru.it.sd.dao.mapper.servicelevel;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.ServiceLevelPriority;
import ru.it.sd.model.ServiceLevelPriorityDuration;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер продолжительности условий приоритета
 *
 * @author nsychev
 */
@Component
public class ServiceLevelPriorityDurationMapper extends EntityRowMapper<ServiceLevelPriorityDuration> {

    private final CodeDao codeDao;

    public ServiceLevelPriorityDurationMapper(CodeDao codeDao) {
        this.codeDao = codeDao;
    }

    @Override
    public ServiceLevelPriorityDuration mapRow(ResultSet rs, int rowNum) throws SQLException {
        ServiceLevelPriorityDuration serviceLevelPriorityDuration = super.mapRow(rs, rowNum);
        if (serviceLevelPriorityDuration == null) return null;
        double duration = rs.getDouble("pds_maximumduration");
        if (duration != 0) {
            //Переводим дату из double в миллисекунды
            Long time = (long)(duration * 24 * 60 * 60 * 1000);
            serviceLevelPriorityDuration.setMaximumDuration(time);
        }
        Long serviceLevelPriorityId = DBUtils.getLong(rs, "pds_priority");
        if (serviceLevelPriorityId != null) {
            BaseCode serviceLevelPriority = codeDao.read(serviceLevelPriorityId);
            serviceLevelPriorityDuration.setServiceLevelPriority(serviceLevelPriority.convertTo(ServiceLevelPriority.class));
        }
        return serviceLevelPriorityDuration;
    }
}
