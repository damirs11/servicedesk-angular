package ru.it.sd.model;

/**
 * Условие приоритета
 *
 * @author nsychev
 */
public class ServiceLevelPriority extends BaseCode {

    private static final long serialVersionUID = 5270563648746819342L;

    public ServiceLevelPriority() {
    }

    public static Long getTypeId(EntityType entityType) {
        //Для всех сущностей одинаковый subtype
        return 887947265L;
    }
}