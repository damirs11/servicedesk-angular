package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.EntityCategory;
import ru.it.sd.model.EntityType;
import ru.it.sd.util.ResourceMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис категорий
 *
 * @author NSychev
 * @since 22.12.2017
 */
@Service
public class CategoryService extends ReadService<EntityCategory> {

    private static final Logger LOG = LoggerFactory.getLogger(StatusService.class);

    private final CodeDao codeDao;

    @Autowired
    public CategoryService(CodeDao codeDao) {
        this.codeDao = codeDao;
    }

    @Override
    public EntityCategory read(long id) {
        BaseCode code = codeDao.read(id);
        return code == null ? null : code.convertTo(EntityCategory.class);
    }

    @Override
    public List<EntityCategory> list(Map<String, String> filter) {
        String entityTypeId = filter.get("entityTypeId");
        if (entityTypeId == null) {
            throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
        }
        Long subType = EntityCategory.getTypeId(EntityType.get(Long.parseLong(entityTypeId)));
        Map<String, String> subFilter = new HashMap<>();
        subFilter.put("subtype", subType.toString());
        List<BaseCode> codes = codeDao.list(subFilter);
        List<EntityCategory> result = new ArrayList<>();
        codes.forEach((code) ->
                result.add(code.convertTo(EntityCategory.class))
        );
        return result;
    }

    @Override
    public int count(Map<String, String> filter) {
        Map<String, String> clearFilter = AbstractEntityDao.clearFilter(filter);
        return list(clearFilter).size();
    }
}
