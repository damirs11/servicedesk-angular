package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.model.EntityType;
import ru.it.sd.model.Notification;
import ru.it.sd.model.ServiceCall;

/**
 * Сервис получения кодов "Уведомление"
 * {@link ServiceCall#getNotification()}
 *
 * @author nsychev
 */
@Service
public class NotificationService extends AbstractCodeService<Notification> {
    @Override
    protected Long getTypeId(EntityType entityType) {
        return Notification.getTypeId(entityType);
    }
}
