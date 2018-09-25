package ru.it.sd.dao.utils;

import org.apache.commons.lang3.StringUtils;
import ru.it.sd.meta.FieldMetaData;
import ru.it.sd.meta.MetaUtils;
import ru.it.sd.model.EntityType;
import ru.it.sd.model.TemplateValue;
import ru.it.sd.util.EntityUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TemplateUtils {

    public static ResultSet getResultSet(List<TemplateValue> templateValues, EntityType entityType) throws SQLException {
        InMemoryResultSet resultSet = new InMemoryResultSet();
        Map<String, FieldMetaData> fmds = MetaUtils.getFieldsMetaData(EntityUtils.getEntityClass(entityType.getAlias()));
        for (FieldMetaData fieldMetaData : fmds.values()) {
            for (TemplateValue templateValue : templateValues) {
                //Если column пустое и атрибуты совпадают
                if (StringUtils.isNotBlank(fieldMetaData.getColumnName())
                        && templateValue.getAttribute().equals(fieldMetaData.getAttribute())
                        && templateValue.getValue() != null) {
                    setParams(resultSet, fieldMetaData, templateValue);
                } else if (Long.valueOf(fieldMetaData.getAttribute()).equals(templateValue.getAggregationId()) && templateValue.getValue() != null) {
                    Map<String, FieldMetaData> subFmds = MetaUtils.getFieldsMetaData(fieldMetaData.getType());
                    for (FieldMetaData subFieldMetaData : subFmds.values()) {
                        if (StringUtils.isNotBlank(subFieldMetaData.getColumnName()) && templateValue.getAttribute().equals(subFieldMetaData.getAttribute())) {
                            setParams(resultSet, subFieldMetaData, templateValue);
                        }
                    }
                }
            }
        }
        return resultSet;
    }

    private static void setParams(ResultSet resultSet, FieldMetaData fieldMetaData, TemplateValue templateValue) throws SQLException {
        switch (fieldMetaData.getType().getName()) {
            case "java.lang.Long": {
                resultSet.updateLong(fieldMetaData.getColumnName(), (Long) templateValue.getValue());
            }
            break;
            case "long": {
                resultSet.updateLong(fieldMetaData.getColumnName(), (Long) templateValue.getValue());
            }

            case "java.lang.Integer": {
                resultSet.updateInt(fieldMetaData.getColumnName(), (Integer) templateValue.getValue());
            }
            break;
            case "int": {
                resultSet.updateInt(fieldMetaData.getColumnName(), (Integer) templateValue.getValue());
            }
            break;

            case "java.lang.String": {
                resultSet.updateString(fieldMetaData.getColumnName(), (String) templateValue.getValue());
            }
            break;
            case "java.lang.Boolean": {
                resultSet.updateBoolean(fieldMetaData.getColumnName(), (Boolean) templateValue.getValue());
            }
            break;
            case "boolean": {
                resultSet.updateBoolean(fieldMetaData.getColumnName(), (Boolean) templateValue.getValue());
            }
            break;
            case "java.util.Date": {
                //todo расчет времени с учетом графика работы
                Float time = Float.valueOf((String)templateValue.getValue());
                Timestamp timestamp = new Timestamp(System.currentTimeMillis() + Math.round(time*24*60*60*1000));
                resultSet.updateTimestamp(fieldMetaData.getColumnName(), timestamp);
            }
            break;
            case "java.lang.Double": {
                resultSet.updateDouble(fieldMetaData.getColumnName(), (Double) templateValue.getValue());
            }
            break;
            case "double": {
                resultSet.updateDouble(fieldMetaData.getColumnName(), (Double) templateValue.getValue());
            }
            break;
            //Для всех остальных классов
            default: {
                String value = (String) templateValue.getValue();
                resultSet.updateLong(fieldMetaData.getColumnName(), Long.valueOf(value));
            }
        }
    }
}
