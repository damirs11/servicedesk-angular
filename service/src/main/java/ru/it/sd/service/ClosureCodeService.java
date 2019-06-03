package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.model.EntityClosureCode;
import ru.it.sd.model.EntityType;

/**
 * Сервис кода завершения
 *
 * @author NSychev
 * @since 22.12.2017
 */
@Service
public class ClosureCodeService extends AbstractCodeService<EntityClosureCode> {

    @Override
    protected Long getTypeId(EntityType entityType) {
        return EntityClosureCode.getTypeId(entityType);
    }
}
