package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;
import java.util.Date;

@ClassMeta(tableName = "itsm_historylines_change", tableAlias ="hch")
public class ChangeChatLine implements HasId, Serializable {

    @FieldMeta(columnName = "hch_oid", key = true)
    private Long id;

    @FieldMeta(columnName = "hch_newvalue")
    private String value;

    @FieldMeta(columnName = "hch_valueatr_oid")
    private ChatLineType type;

    @FieldMeta(columnName = "reg_created_by_oid")
    private User account;

    @FieldMeta(columnName = "reg_created")
    private Date date;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ChatLineType getType() {
        return type;
    }

    public void setType(ChatLineType type) {
        this.type = type;
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
}
