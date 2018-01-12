package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.io.Serializable;

/**
 * Модель доступа к атрибутам
 * Created by NSychev
 */
@ClassMeta(tableName = "rep_attribute_access", tableAlias = "raa")
public class AttributeAccess implements HasId, Serializable {

    @FieldMeta(columnName = "ata_oid", key = true)
    private Long id;

    @FieldMeta(columnName = "ata_modify")
    private Boolean modify;

    @FieldMeta(columnName = "ata_atr_oid")
    private Long attributeId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getModify() {
        return modify;
    }

    public void setModify(Boolean access) {
        this.modify = access;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

}
