package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

/**
 * бренд
 *
 * Created by MYXOMOPX on 013 13.06.17.
 */
@ClassMeta(tableName = "itsm_codes_locale")
public class Brand implements Code, HasId {
    /** Идентификатор */
    @FieldMeta(columnName = "cdl_cod_oid", key = true)
    private Long id;
    /** Название */
    @FieldMeta(columnName = "cdl_name")
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
        return ToStringBuilder.reflectionToString(this, AppToStringStyle.getInstance());
    }
}