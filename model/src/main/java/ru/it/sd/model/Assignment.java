package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.io.Serializable;

@ClassMeta(tableName = "")
public class Assignment implements HasStatus, Serializable {

    private static final long serialVersionUID = 8997994525571656956L;

    @FieldMeta(columnName = "ass_status")
    private EntityStatus status;

    @FieldMeta(columnName = "ass_priority")
    private EntityPriority priority;

    @FieldMeta(columnName = "ass_person_to")
    private Person executor;

    @FieldMeta(columnName = "ass_workgroup_to")
    private Workgroup workgroup;

    @Override
    public EntityStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(EntityStatus id) {
        this.status = id;
    }

    public EntityPriority getPriority() {
        return priority;
    }

    public void setPriority(EntityPriority priority) {
        this.priority = priority;
    }

    public Person getExecutor() {
        return executor;
    }

    public void setExecutor(Person executor) {
        this.executor = executor;
    }

    public Workgroup getWorkgroup() {
        return workgroup;
    }

    public void setWorkgroup(Workgroup workgroup) {
        this.workgroup = workgroup;
    }
}
