package ru.it.sd.model;

import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

/**
 * Сопоставление типа для максимальной продолжительности к типу сущности
 */
public enum ServiceLevelPriorityDurationType {

    SERVICECALL(427236L, EntityType.SERVICECALL);

    private Long type;
    private EntityType entityType;

    ServiceLevelPriorityDurationType(Long type, EntityType entityType) {
        this.type = type;
        this.entityType = entityType;
    }

    public Long getType() {
        return type;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public static ServiceLevelPriorityDurationType findByEntityType(EntityType entityType) {
        for (ServiceLevelPriorityDurationType value : values()) {
            if (value.entityType == entityType) {
                return value;
            }
        }
        throw new ServiceException(ResourceMessages.getMessage("error.not.found"));
    }

    public static ServiceLevelPriorityDurationType findByEntityType(long entityTypeId) {
        return findByEntityType(EntityType.get(entityTypeId));
    }
}
