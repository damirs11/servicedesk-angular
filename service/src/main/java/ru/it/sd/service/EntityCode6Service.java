package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.model.EntityCode6;
import ru.it.sd.model.EntityType;

/**
 * Сервис Систем
 *
 * @author NSychev
 * @since 22.12.2017
 */
@Service
public class EntityCode6Service extends AbstractCodeService<EntityCode6> {
    @Override
    protected Long getTypeId(EntityType entityType) {
        return EntityCode6.getTypeId(entityType);
    }
}
