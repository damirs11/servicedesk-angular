package ru.it.sd.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Created by MYXOMOPX on 029 29.08.17.
 */
public enum HistoryLineType {
    CHANGE_INITIATOR("INITIATOR",724041771L),
    CHANGE_MANAGER("MANAGER",281484032738115L);

    String name;
    Long fieldId;

    HistoryLineType(String name, Long fieldId) {
        this.name = name;
        this.fieldId = fieldId;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }


    public static HistoryLineType getByField(Long id) {
        if (Objects.isNull(id)) {
            return null;
        }
        for (HistoryLineType value : values()) {
            if ( id.equals( value.getFieldId() ) ) {
                return value;
            }
        }
        return null;
    }
}