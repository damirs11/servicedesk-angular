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
import ru.it.sd.model.EntityCode1;
import ru.it.sd.util.ResourceMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис Систем
 *
 * @author NSychev
 * @since 22.12.2017
 */
@Service
public class EntityCode1Service extends ReadService<EntityCode1> {

    private static final Logger LOG = LoggerFactory.getLogger(StatusService.class);

    private final CodeDao codeDao;

    @Autowired
    public EntityCode1Service(CodeDao codeDao) {
        this.codeDao = codeDao;
    }

    @Override
    public EntityCode1 read(long id) {
        BaseCode code = codeDao.read(id);
        return code == null ? null : code.convertTo(EntityCode1.class);
    }

    @Override
    public List<EntityCode1> list(Map<String, String> filter) {
        String entityTypeId = filter.get("entityTypeId");
        if (entityTypeId == null) {
            throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
        }
        Long subType = EntityPriority.getTypeId(EntityType.get(Long.parseLong(entityTypeId)));
        Map<String, String> subFilter = new HashMap<>();
        subFilter.put("subtype", subType.toString());
        List<BaseCode> codes = codeDao.list(subFilter);
        List<EntityCode1> result = new ArrayList<>();
        codes.forEach((code) ->
                result.add(code.convertTo(EntityCode1.class))
        );
        return result;
    }

    @Override
    public int count(Map<String, String> filter) {
        Map<String, String> clearFilter = AbstractEntityDao.clearFilter(filter);
        return list(clearFilter).size();
    }
}
