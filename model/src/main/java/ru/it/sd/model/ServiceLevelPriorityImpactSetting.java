package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

/**
 * Модельный класс для настроек условия приоритета по отношению к приоритету сущности
 * @author nsychev
 */
@ClassMeta(tableName = "itsm_priorityimpactsettings", tableAlias = "pis")
public class ServiceLevelPriorityImpactSetting implements HasId {

    @FieldMeta(columnName = "pis_oid", key = true)
    private Long id;
    @FieldMeta(columnName = "pis_pri_oid")
    private ServiceLevelPriority serviceLevelPriority;
    @FieldMeta(columnName = "pis_imp_oid")
    private EntityPriority priority;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ServiceLevelPriority getServiceLevelPriority() {
        return serviceLevelPriority;
    }

    public void setServiceLevelPriority(ServiceLevelPriority serviceLevelPriority) {
        this.serviceLevelPriority = serviceLevelPriority;
    }

    public EntityPriority getPriority() {
        return priority;
    }

    public void setPriority(EntityPriority priority) {
        this.priority = priority;
    }
}
