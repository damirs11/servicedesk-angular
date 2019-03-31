package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.util.Date;

@ClassMeta(tableName = "itsm_historylines_servicecall", tableAlias ="hsc")
public class ServicecallHistory implements EntityHistory {

    private static final long serialVersionUID = 1769190659807231967L;

    @FieldMeta(columnName = "hsc_oid", key = true)
    private Long id;

    @FieldMeta(columnName = "hsc_subject")
    private String subject;

    @FieldMeta(columnName = "reg_createdby_oid")
    private User account;

    @FieldMeta(columnName = "hsc_created")
    private Date date;

    @FieldMeta(columnName = "hsc_newvalue")
    private String value;

    @FieldMeta(columnName = "type")
    private HistoryType type;

    private Boolean isOwner;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public User getAccount() {
        return account;
    }

    public void setAccount(User account) {
        this.account = account;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HistoryType getType() {
        return type;
    }

    public void setType(HistoryType type) {
        this.type = type;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }
}
