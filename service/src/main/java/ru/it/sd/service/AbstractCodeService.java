package ru.it.sd.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.EntityType;
import ru.it.sd.util.ResourceMessages;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractCodeService<T extends BaseCode> extends ReadService<T> {

    @Autowired
    private CodeDao codeDao;

    @Override
    public T read(long id) {
        return codeDao.read(id).convertTo(getGenericClass());
    }

    @Override
    public List<T> list(Map<String, String> filter) {
        String entityTypeId = filter.get("entityTypeId");
        if (entityTypeId == null) {
            throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
        }
        Long subType = getTypeId(EntityType.get(Long.parseLong(entityTypeId)));
        Map<String, String> subFilter = new HashMap<>();
        subFilter.put("subtype", subType.toString());
        if (filter.get("disabled") == null) {
            subFilter.put("disabled", "0");
        }
        List<BaseCode> codes = codeDao.list(subFilter);
        return codes.stream().map(code -> code.convertTo(getGenericClass())).collect(Collectors.toList());
    }

    @Override
    public int count(Map<String, String> filter) {
        return codeDao.count(filter);
    }

    @SuppressWarnings("unchecked")
    private Class<T> getGenericClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }

    protected abstract Long getTypeId(EntityType entityType);
}
