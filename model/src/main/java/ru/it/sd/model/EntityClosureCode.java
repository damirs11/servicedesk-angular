package ru.it.sd.model;

import java.util.Objects;

import static ru.it.sd.model.EntityType.WORKORDER;

/**
 * Created by MYXOMOPX on 013 13.06.17.
 */

/**
 * Код завершения.
 */
public enum EntityClosureCode implements Code {

    WORKORDER_COMPLETED("Осуществлен",WORKORDER,3095134405L),
    WORKORDER_REJECTED("Отвергнут",WORKORDER,3095134406L),
    WORKORDER_CANCELED("Отменен",WORKORDER,3095134407L),
    WORKORDER_REPEATED("Повторный",WORKORDER,3095134408L);


    EntityClosureCode(String name, EntityType entityType, Long id){
        this.name = name;
        this.entityType = entityType;
        this.id = id;
    }

    /** Тип сущности, к которой относится код завершения */
    private EntityType entityType;
    /** название */
    private String name;
    /** идентификатор */
    private Long id;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }


    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    /**
     * Поиск код завершения по его идентификатору
     * @return код завершения, либо null, если указанного идентификатора нет
     */
    public static EntityClosureCode getById(Long id) {
        Objects.requireNonNull(id);
        for(EntityClosureCode closureCode : EntityClosureCode.values()) {
            if (id.equals(closureCode.id)) {
                return closureCode;
            }
        }
        return null;
    }
}