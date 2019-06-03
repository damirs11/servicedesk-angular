package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.model.EntityType;
import ru.it.sd.model.ResponsibilityArea;
import ru.it.sd.model.ServiceCall;

/**
 * Сервис получения кодов "Зона ответственности"
 * {@link ServiceCall#getResponsibilityArea()}
 *
 * @author nsychev
 */
@Service
public class ResponsibilityAreaService extends AbstractCodeService<ResponsibilityArea> {
    @Override
    protected Long getTypeId(EntityType entityType) {
        return ResponsibilityArea.getTypeId(entityType);
    }
}
