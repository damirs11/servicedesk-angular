package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.model.EntityCode7;
import ru.it.sd.model.EntityType;

/**
 * Сервис Систем
 *
 * @author NSychev
 * @since 22.12.2017
 */
@Service
public class EntityCode7Service extends AbstractCodeService<EntityCode7> {

    @Override
    protected Long getTypeId(EntityType entityType) {
        return EntityCode7.getTypeId(entityType);
    }
}
