package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;

/**
 * Created by user on 22.07.2017.
 */
@ClassMeta(tableName = "itsm_workgroups", tableAlias = "wg")
public class Workgroup implements HasId, HasStatus, HasFolder, Serializable {

    private static final long serialVersionUID = -1010139758781532358L;

    public Workgroup() {
    }

    public Workgroup(Long id) {
        this.id = id;
    }

    @FieldMeta(columnName = "wog_oid", key = true)
    private Long id;

    @FieldMeta(columnName = "wog_name")
    private String name;

    @FieldMeta(columnName = "wog_searchcode")
    private String searchcode;

    @FieldMeta(columnName = "wog_sta_oid")
    private EntityStatus status;

    @FieldMeta(columnName = "wog_parent")
    private Workgroup parent;

    @FieldMeta(columnName = "wog_poo_oid")
    private Folder folder;
    @FieldMeta(columnName = "wog_notselectable")
    private Boolean blocked;

    /**
     * Ответственный по группе
     */
    @FieldMeta(columnName = "wgc_per1_oid", tableAlias = "wgc")
    private Person groupManager;

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

    public Workgroup getParent() {
        return parent;
    }

    public void setParent(Workgroup parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, AppToStringStyle.getInstance());
    }

    @Override
    public Folder getFolder() {
        return folder;
    }

    @Override
    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public Person getGroupManager() {
        return groupManager;
    }

    public void setGroupManager(Person groupManager) {
        this.groupManager = groupManager;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }
}
