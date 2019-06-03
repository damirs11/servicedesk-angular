package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.model.EntityCode3;
import ru.it.sd.model.EntityType;

/**
 * Сервис для "Кастомный код 3"
 *
 * @author NSychev
 * @since 22.12.2017
 */
@Service
public class EntityCode3Service extends AbstractCodeService<EntityCode3> {

    @Override
    protected Long getTypeId(EntityType entityType) {
        return EntityCode3.getTypeId(entityType);
    }
}
