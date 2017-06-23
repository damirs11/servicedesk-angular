package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

/**
 * Created by MYXOMOPX on 013 13.06.17.
 */

/**
 * Папка доступа
 */
@ClassMeta(tableName = "REP_CODES_TEXT")
public class Folder implements Code {
    /** Идентификатор */
    @FieldMeta(columnName = "RCT_RCD_OID")
    private Long id;
    /** Название */
    @FieldMeta(columnName = "RCT_NAME")
    private String name;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, AppToStringStyle.getInstance())
                .append("id", this.id)
                .append("name", this.name)
                .toString();
    }
}
