package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.model.EntityType;
import ru.it.sd.model.FAQ;
import ru.it.sd.model.ServiceCall;

/**
 * Сервис получения кодов "Часто задаваемые вопросы"
 * {@link ServiceCall#getResponsibilityArea()} ()}
 *
 * @author nsychev
 */
@Service
public class FAQService extends AbstractCodeService<FAQ> {
    @Override
    protected Long getTypeId(EntityType entityType) {
        return FAQ.getTypeId(entityType);
    }
}
