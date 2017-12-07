package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;
import java.util.Date;

@ClassMeta(tableName = "itsm_historylines_workorder", tableAlias ="hwk")
public class WorkorderHistory implements HasId, Serializable {

    private static final long serialVersionUID = 5544537153486735750L;

    @FieldMeta(columnName = "hwk_oid", key = true)
    private Long id;

    @FieldMeta(columnName = "hwk_subject")
    private String subject;

    @FieldMeta(columnName = "reg_created_by_oid")
    private User account;

    @FieldMeta(columnName = "reg_created")
    private Date date;

    @FieldMeta(columnName = "hwk_newvalue")
    private String value;

    @FieldMeta(columnName = "type")
    private HistoryType type;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, AppToStringStyle.getInstance());
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
