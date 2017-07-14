package ru.it.sd.dao;

import org.apache.commons.collections.MultiMap;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import ru.it.sd.meta.FieldMetaData;
import ru.it.sd.meta.MetaUtils;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by NSychev on 12.07.2017.
 */
public class FilterUtils {
    //
    /**
     * Добовляет к запросу условия для данного класса по фильтру
     * @param queryPart sql запрос сформированный до начала фильтра
     * @param params
     * @param clazz модель по которой будет происходить поиск аннотированных полей
     * @param filter фильтр(Map<String, String>)
     * @param filterFields мапа в которой хранятся таблица:{поле, поле2} для определения из какой таблицы взято поле
     */
    public static void createFilter(StringBuilder queryPart, MapSqlParameterSource params, Map<String, String> filter, MultiMap filterFields, Class clazz){


        if((filter != null) && !filter.entrySet().isEmpty()) {
            // Временно !!!!
            queryPart.append("\n WHERE TRUE");

            //Поиск по всем полям класса
            List<FieldMetaData> fieldMetaDataList = MetaUtils.getFieldsMetaData(clazz);
            for (FieldMetaData fmd : fieldMetaDataList) {

                //Если фильтр для данного поля существует key == field name
                if (filter.containsKey(fmd.getName())) {
                    //Отсеивание сложных объектов и приведение типов
                    if(setParams(fmd, params, filter.get(fmd.getName()))) {
                        //Получение префикса таблицы по columnName в мапе filterFields
                        String prefix = getPrefix(filterFields,fmd);
                        if(prefix != null) {
                            queryPart.append(getExpression(fmd,prefix,"LIKE"));
                        }
                    }
                }
            }
        }
    }

    private static String getExpression(FieldMetaData fmd, String prefix, String type){
        StringBuilder expression = new StringBuilder();
        if(type.equals("LIKE")){
            expression.
                    append("\n\tAND ").
                    append(prefix).
                    append(".").
                    append(fmd.getColumnName()).
                    append(" LIKE :").
                    append(fmd.getName()).
                    append("");
            return expression.toString();
        }
        //Обдумать как параметры в MapSqlParamSource записывать
        if(type == "LIST"){
            expression.
                    append("\n\tOR ").
                    append(prefix).
                    append(".").
                    append(fmd.getColumnName()).
                    //append(" LIKE '%:").
                    append(" = :").
                    append(fmd.getName());
                    //append("%'");
            return expression.toString();
        }


        return "";
    }

    private static String getPrefix(MultiMap filterFields, FieldMetaData fmd){
        String prefix = null;
        for(Object str : filterFields.keySet()){
            ArrayList<String> values = (ArrayList<String>)filterFields.get(str);
            for(String val : values)
            {
                if(fmd.getColumnName().equals(val)){
                    prefix = (String)str;
                    break;
                }
            }
            if(prefix != null) break;
        }
        return prefix;
    }



    private static boolean setParams(FieldMetaData fmd, MapSqlParameterSource params, String value){
        switch(fmd.getType().getName()){
            case "java.lang.Long": {
                try {
                    Long val = Long.parseLong(value);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;
            }
            case "long": {
                try {
                    long val = Long.parseLong(value);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;
            }

            case "java.lang.Integer":{
                try {
                    Integer val = Integer.valueOf(value);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;

            }
            case "int": {
                try {
                    int val = Integer.valueOf(value);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;
            }

            case "java.lang.String": {
                String val = "%"+value+"%";
                params.addValue(fmd.getName(),val);
                return true;
            }

            case "java.lang.Boolean":{
                try {
                    Boolean val = Boolean.valueOf(value);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;

            }
            case "boolean": {

                try {
                    boolean val = Boolean.valueOf(value);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;
            }

            case "java.util.Date":{
                try {
                    Long val = Long.parseLong(value);
                    Timestamp date = new Timestamp(val);
                    params.addValue(fmd.getName(), date);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;
            }

            case "java.lang.Double":{
                if(value.indexOf(",")>-1){
                    value = value.replace(",", ".");
                }
                try {
                    Double val = Double.valueOf(value);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;
            }
            case "double":{
                try {
                    double val = Double.valueOf(value);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;
            }

            case "ru.it.sd.model.Person":{
                try {
                    Long val = Long.parseLong(value);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;
            }
        }

        return false;
    }

}
