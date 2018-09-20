package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;

/**
 * Модель значений шаблона
 * @author nsychev
 * @since 05.09.2018
 */
@ClassMeta(tableName = "rep_template_values", tableAlias = "tv")
public class TemplateValue implements HasId, Serializable {

    private static final long serialVersionUID = -2169656457023869007L;

    @FieldMeta(columnName = "tva_oid", key = true)
    private Long id;
    @FieldMeta(columnName = "tva_atr_oid")
    private Long attribute;
    @FieldMeta(columnName = "tva_value")
    private Object value;
    @FieldMeta(columnName = "tva_atr_aggregation_oid")
    private Long aggregationId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttribute() {
        return attribute;
    }

    public void setAttribute(Long attribute) {
        this.attribute = attribute;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Long getAggregationId() {
        return aggregationId;
    }

    public void setAggregationId(Long aggregationId) {
        this.aggregationId = aggregationId;
    }
}
