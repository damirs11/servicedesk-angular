package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.EntityPriority;
import ru.it.sd.model.EntityType;
import ru.it.sd.util.ResourceMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис приоритетов
 *
 * @author NSychev
 * @since 22.12.2017
 */
@Service
public class PriorityService extends ReadService<EntityPriority> {

    private static final Logger LOG = LoggerFactory.getLogger(StatusService.class);

    private final CodeDao codeDao;

    @Autowired
    public PriorityService(CodeDao codeDao) {
        this.codeDao = codeDao;
    }

    @Override
    public EntityPriority read(long id) {
        BaseCode code = codeDao.read(id);
        return code == null ? null : code.convertTo(EntityPriority.class);
    }

    @Override
    public List<EntityPriority> list(Map<String, String> filter) {
        String entityTypeId = filter.get("entityTypeId");
        if (entityTypeId == null) {
            throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
        }
        Long subType = EntityPriority.getTypeId(EntityType.get(Long.parseLong(entityTypeId)));
        Map<String, String> subFilter = new HashMap<>();
        subFilter.put("subtype", subType.toString());
        if (filter.get("disabled") == null) {
            subFilter.put("disabled", "0");
        }
        List<BaseCode> codes = codeDao.list(subFilter);
        List<EntityPriority> result = new ArrayList<>();
        codes.forEach((code) ->
                result.add(code.convertTo(EntityPriority.class))
        );
        return result;
    }

    @Override
    public int count(Map<String, String> filter) {
        Map<String, String> clearFilter = AbstractEntityDao.clearFilter(filter);
        return list(clearFilter).size();
    }
}
