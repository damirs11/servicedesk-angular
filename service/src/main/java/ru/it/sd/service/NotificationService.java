package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.EntityType;
import ru.it.sd.model.Notification;
import ru.it.sd.model.ServiceCall;
import ru.it.sd.util.ResourceMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис получения кодов "Уведомление"
 * {@link ServiceCall#getNotification()}
 *
 * @author nsychev
 */
@Service
public class NotificationService extends ReadService<Notification> {

    private final CodeDao codeDao;

    public NotificationService(CodeDao codeDao) {
        this.codeDao = codeDao;
    }

    @Override
    public Notification read(long id) {
        BaseCode code = codeDao.read(id);
        return code == null ? null : code.convertTo(Notification.class);
    }

    @Override
    public List<Notification> list(Map<String, String> filter) {
        String entityTypeId = filter.get("entityTypeId");
        if (entityTypeId == null) {
            throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
        }
        Long subType = Notification.getTypeId(EntityType.get(Long.parseLong(entityTypeId)));
        Map<String, String> subFilter = new HashMap<>();
        subFilter.put("subtype", subType.toString());
        if (filter.get("disabled") == null) {
            subFilter.put("disabled", "0");
        }
        List<BaseCode> codes = codeDao.list(subFilter);
        List<Notification> result = new ArrayList<>();
        codes.forEach((code) ->
                result.add(code.convertTo(Notification.class))
        );
        return result;
    }

    @Override
    public int count(Map<String, String> filter) {
        Map<String, String> clearFilter = AbstractEntityDao.clearFilter(filter);
        return list(clearFilter).size();
    }

}