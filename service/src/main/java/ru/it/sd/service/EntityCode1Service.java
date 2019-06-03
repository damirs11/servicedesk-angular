package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.model.EntityCode1;
import ru.it.sd.model.EntityType;

/**
 * Сервис Систем
 *
 * @author NSychev
 * @since 22.12.2017
 */
@Service
public class EntityCode1Service extends AbstractCodeService<EntityCode1> {

    @Override
    protected Long getTypeId(EntityType entityType) {
        return EntityCode1.getTypeId(entityType);
    }
}
