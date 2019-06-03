package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.model.EntityType;
import ru.it.sd.model.Source;

/**
 * Сервис "источник"
 *
 * @author NSychev
 * @since 22.12.2017
 */
@Service
public class SourceService extends AbstractCodeService<Source> {
    @Override
    protected Long getTypeId(EntityType entityType) {
        return Source.getTypeId(entityType);
    }
}
