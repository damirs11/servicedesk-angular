package ru.it.sd.model;

/**
 * FAQ
 *
 * @author NSychev
 * @since 15.05.2019
 */
public class FAQ extends BaseCode {

    private static final long serialVersionUID = 4889770089180475611L;

    public FAQ() {
    }

    public static Long getTypeId(EntityType entityType) {
        if (entityType == EntityType.SERVICECALL) {
            return 3072393222L;
        }
        throw new UnsupportedOperationException();
    }
}