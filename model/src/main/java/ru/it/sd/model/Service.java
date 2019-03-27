package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

/**
 * Услуга
 * @author nsychev
 */
@ClassMeta(tableName = "itsm_services", tableAlias = "srv")
public class Service implements HasId, HasFolder {

    @FieldMeta(columnName = "srv_oid", key = true)
    private Long id;

    @FieldMeta(columnName = "srv_name")
    private String name;

    @FieldMeta(columnName = "srv_pool_cod_oid")
    private Folder folder;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Folder getFolder() {
        return folder;
    }

    @Override
    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
