package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;

/**
 * Created by user on 22.07.2017.
 */
@ClassMeta(tableName = "rep_templates", tableAlias = "tem")
public class Template implements HasId, Serializable {


    private static final long serialVersionUID = -2169656457023869007L;

    @FieldMeta(columnName = "tem_oid", key = true)
    private Long id;

    @FieldMeta(columnName = "tem_name")
    private String name;

    @FieldMeta(columnName = "tem_ent_oid")
    private EntityType entityType;

    @FieldMeta(columnName = "tem_rcd_oid")
    private Folder folder;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, AppToStringStyle.getInstance());
    }

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

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}
