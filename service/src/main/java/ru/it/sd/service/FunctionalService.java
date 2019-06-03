package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.model.EntityType;
import ru.it.sd.model.Functional;
import ru.it.sd.model.ServiceCall;

/**
 * Сервис получения кодов "Функционал"
 * {@link ServiceCall#getFunctional()}
 *
 * @author nsychev
 */
@Service
public class FunctionalService extends AbstractCodeService<Functional> {

    @Override
    protected Long getTypeId(EntityType entityType) {
        return Functional.getTypeId(entityType);
    }
}
