package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Модельный класс для "Условия предоставления"
 * @author nsychev
 */
@ClassMeta(tableName = "itsm_service_level", tableAlias = "sel")
public class ServiceLevel implements HasId, Serializable {

    private static final long serialVersionUID = -8229054884264481809L;

    public ServiceLevel() {
    }

    public ServiceLevel(Long id) {
        this.id = id;
    }

    @FieldMeta(columnName = "sel_oid", key = true)
    private Long id;
    /**
     * Имя
     */
    @FieldMeta(columnName = "sel_name")
    private String name;
    /**
     * Описание
     */
    @FieldMeta(columnName = "sel_description")
    private String description;
    /**
     * Является условием предоствления по умолчанию (может быть только один среди всех условий)
     */
    @FieldMeta(columnName = "sel_default")
    private Boolean defaultValue;
    /**
     * Заблокирован
     */
    @FieldMeta(columnName = "sel_blocked")
    private Boolean blocked;

    private Collection<ServiceLevelPriorityImpactSetting> impactSettingList = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Collection<ServiceLevelPriorityImpactSetting> getImpactSettingList() {
        return impactSettingList;
    }

    public void setImpactSettingList(Collection<ServiceLevelPriorityImpactSetting> impactSettingList) {
        this.impactSettingList = impactSettingList;
    }
}
