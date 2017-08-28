package ru.it.sd.model;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@ClassMeta(tableName = "itsm_historylines_change", tableAlias ="hch")
public class ChangeHistoryLine implements HasId, Serializable {

    @FieldMeta(columnName = "hch_oid", key = true)
    private Long id;

    @FieldMeta(columnName = "hch_subject")
    private String subject;

    @FieldMeta(columnName = "reg_created_by_oid")
    private User account;

    @FieldMeta(columnName = "reg_created")
    private Date date;

    @FieldMeta(columnName = "hch_newvalue")
    private String value;

    @FieldMeta(columnName = "chatSender")
    private ChatSender chatSender;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ChatSender getChatSender() {
        return chatSender;
    }

    public void setChatSender(ChatSender chatSender) {
        this.chatSender = chatSender;
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

    public enum ChatSender {
        INITIATOR(0L,724041771L),
        MANAGER(1L,281484032738115L);

        Long id;
        Long fieldId;

        @JsonValue
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getFieldId() {
            return fieldId;
        }

        public void setFieldId(Long fieldId) {
            this.fieldId = fieldId;
        }

        ChatSender(Long id, Long fieldId) {
            this.id = id;
            this.fieldId = fieldId;
        }

        public static ChatSender get(Long id) {
            if (Objects.isNull(id)) {
                return null;
            }
            for (ChatSender value : values()) {
                if (id.equals(value.id)) {
                    return value;
                }
            }
            return null;
        }
    }

}
