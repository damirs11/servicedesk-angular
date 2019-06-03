package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.model.EntityCategory;
import ru.it.sd.model.EntityType;

/**
 * Сервис категорий
 *
 * @author NSychev
 * @since 22.12.2017
 */
@Service
public class CategoryService extends AbstractCodeService<EntityCategory> {

    @Override
    protected Long getTypeId(EntityType entityType) {
        return EntityCategory.getTypeId(entityType);
    }
}
