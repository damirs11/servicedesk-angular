package ru.it.sd.model;

import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

/**
 * Дефолтный приоритет SLA
 *
 * @author nsychev
 * @since 16.05.2019
 */
public class DefaultPriority extends BaseCode {

    private static final long serialVersionUID = 4889770089180475611L;

    public DefaultPriority() {
    }

    public static Long getTypeId(EntityType entityType) {

        if (entityType == EntityType.SERVICE_LEVEL_AGREEMENT) {
            return 281478354436219L;
        }
        throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
    }
}