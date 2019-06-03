package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.model.EntityClassification;
import ru.it.sd.model.EntityType;

/**
 * Сервис классификаций
 *
 * @author NSychev
 * @since 22.12.2017
 */
@Service
public class ClassificationService extends AbstractCodeService<EntityClassification> {

    @Override
    protected Long getTypeId(EntityType entityType) {
        return EntityClassification.getTypeId(entityType);
    }
}
