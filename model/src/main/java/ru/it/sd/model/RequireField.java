package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

@ClassMeta(tableName = "ifc_attributes")
public class RequireField implements HasId {

    @FieldMeta(columnName = "atr_oid", key = true)
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
