package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.io.Serializable;

/**
 * Created by user on 22.07.2017.
 */
@ClassMeta(tableName = "itsm_workgroups", tableAlias = "wg")
public class Workgroup implements HasId, HasStatus, Serializable {

    @FieldMeta(columnName = "wog_oid")
    private Long id;

    @FieldMeta(columnName = "wog_name")
    private String name;

    @FieldMeta(columnName = "wog_searchcode")
    private String searchcode;

    @FieldMeta(columnName = "wog_sta_oid")
    private EntityStatus status;

    @FieldMeta(columnName = "slc_wog_per_oid", tableAlias = "per")
    private Person creator;




    @Override
    public EntityStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(EntityStatus status) {
        this.status = status;
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

    public String getSearchcode() {
        return searchcode;
    }

    public void setSearchcode(String searchcode) {
        this.searchcode = searchcode;
    }

    @Override
    public String toString() {
        return "Workgroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", searchcode='" + searchcode + '\'' +
                ", status=" + status +
                '}';
    }
}
