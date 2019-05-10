package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.io.Serializable;

@ClassMeta(tableName = "itsm_prioritydurationsettings", tableAlias = "pds")
public class ServiceLevelPriorityDuration implements HasId, Serializable {

    private static final long serialVersionUID = 2794503672192218858L;

    @FieldMeta(columnName = "pds_oid", key = true)
    private Long id;
    @FieldMeta(columnName = "pds_priority")
    private ServiceLevelPriority serviceLevelPriority;
    @FieldMeta(columnName = "pds_maximumduration")
    private Long maximumDuration;

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

    public Long getMaximumDuration() {
        return maximumDuration;
    }

    public void setMaximumDuration(Long maximumDuration) {
        this.maximumDuration = maximumDuration;
    }
}
