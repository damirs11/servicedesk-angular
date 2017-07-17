package ru.it.sd.dao;

import org.apache.commons.collections.MultiMap;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import ru.it.sd.meta.FieldMetaData;
import ru.it.sd.meta.MetaUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.*;

/**
 * Статический класс добвления фильтров в запрос
 *
 * @author Nsychev
 * @since 14.07.2017
 */
public class FilterUtils {
    //

    //Типы формирования условия
    private enum Type {LIKE, LIST, BETWEEN, BEFORE, AFTER};

    /**
     * Добовляет к запросу условия для данного класса по фильтру
     * @param queryPart запрос в который добавляются фильтры поиска (WHERE .... AND (.....) AND (....) OR (....))
     * @param params список параметров, которые вставляются в запрос при его отправке в бд
     * @param clazz модель по которой будет происходить поиск аннотированных полей
     * @param filter фильтр(Map<String, String>)
     * Пример:
     * ("id","1234;12345;1")
     * ("no","1123")
     * ("purchaseDate", "1045645465000")
     * ("purchaseDate_before", "1045645465000")
     * ("purchaseDate_after", "1045645465000")
     * ("purchaseDate_between", "1045645465000:1245645465000")
     * @param filterFields мапа в которой передается сет таблиц в качестве ключа и список полей этой таблицы для определения префкса таблицы при формировании запроса
     * Пример:
     * ("ITEM","CIT_PURCHASEDATE")
     * ("ITEM","CIT_OID")
     * ("ITEM","CIT_ID")
     */
    public static void createFilter(StringBuilder queryPart, MapSqlParameterSource params, Map<String, String> filter, MultiMap filterFields, Class clazz){

        if((filter != null) && !filter.entrySet().isEmpty()) {
            // Временно !!!!
            queryPart.append("\n WHERE TRUE");

            //Поиск по всем полям класса
            List<FieldMetaData> fieldMetaDataList = MetaUtils.getFieldsMetaData(clazz);
            for (FieldMetaData fmd : fieldMetaDataList) {

                //Если фильтр для данного поля существует key == field name и поиск по списку и одиночному значению
                if (filter.containsKey(fmd.getName())) {
                    Type type = Type.LIKE;
                    //Разбиваем значение filter на массив данных
                    String[] valueArr = filter.get(fmd.getName()).split(";");
                    if(valueArr.length == 1) type = Type.LIKE;
                    if(valueArr.length > 1) type = Type.LIST;

                    //Отсеивание сложных объектов и приведение типов
                    if(setParams(fmd, params, valueArr, type)) {
                        //Получение префикса таблицы по columnName в мапе filterFields
                        String prefix = getPrefix(filterFields,fmd);
                        if(prefix != null) {
                            queryPart.append(getExpression(fmd,prefix,valueArr, type));
                        }
                    }
                }
                //Поиск до
                if(filter.containsKey(fmd.getName()+"_before")){
                    Type type = Type.BEFORE;
                    String[] valueArr = new String[1];
                    valueArr[0] = filter.get(fmd.getName()+"_before");

                    if(setParams(fmd, params, valueArr, type)) {
                        //Получение префикса таблицы по columnName в мапе filterFields
                        String prefix = getPrefix(filterFields,fmd);
                        if(prefix != null) {
                            queryPart.append(getExpression(fmd, prefix, valueArr, type));
                        }
                    }
                }
                //Поиск после
                if(filter.containsKey(fmd.getName()+"_after")){
                    Type type = Type.AFTER;
                    String[] valueArr = new String[1];
                    valueArr[0] = filter.get(fmd.getName()+"_after");

                    if(setParams(fmd, params, valueArr, type)) {
                        //Получение префикса таблицы по columnName в мапе filterFields
                        String prefix = getPrefix(filterFields,fmd);
                        if(prefix != null) {
                            queryPart.append(getExpression(fmd, prefix, valueArr, type));
                        }
                    }
                }
                //Поиск между
                if(filter.containsKey(fmd.getName()+"_between")){
                    Type type = Type.BETWEEN;
                    String[] valueArr = filter.get(fmd.getName()+"_between").split(":");

                    if(setParams(fmd, params, valueArr, type)) {
                        //Получение префикса таблицы по columnName в мапе filterFields
                        String prefix = getPrefix(filterFields,fmd);
                        if(prefix != null) {
                            queryPart.append(getExpression(fmd, prefix, valueArr, type));
                        }
                    }

                }
            }
        }
    }

    private static String getExpression(FieldMetaData fmd, String prefix, String[]values,  Type type){
        StringBuilder expression = new StringBuilder();
        //Формирование условия для 1 значения
        if(type == Type.LIKE){
            expression.
                    append("\n\tAND ").
                    append(prefix).
                    append(".").
                    append(fmd.getColumnName()).
                    append(" LIKE '%'||:").
                    append(fmd.getName()).
                    append("||'%'");
            return expression.toString();
        }
        //Формирование условия для списка значений
        if(type == Type.LIST){
            expression.append("\n\tAND (");
            for(int i = 0; i<values.length;i++)
            {
                expression.
                        append(prefix).
                        append(".").
                        append(fmd.getColumnName()).
                        append(" = :").
                        append(fmd.getName()+i);
                if(i != values.length -1) expression.append(" OR ");
            }
            expression.append(")");
            return expression.toString();
        }
        //Формирование условия для промежутка
        if(type == Type.BETWEEN){
            expression.
                    append("\n\tAND ").
                    append(prefix).
                    append(".").
                    append(fmd.getColumnName()).
                    append(" BETWEEN :").
                    append(fmd.getName()+"_from").
                    append(" AND :").
                    append(fmd.getName()+"_to");
            return expression.toString();
        }
        if(type == Type.BEFORE){
            expression.
                    append("\n\tAND ").
                    append(prefix).
                    append(".").
                    append(fmd.getColumnName()).
                    append(" <= :").
                    append(fmd.getName()+"_before");
            return expression.toString();
        }
        if(type == Type.AFTER){
            expression.
                    append("\n\tAND ").
                    append(prefix).
                    append(".").
                    append(fmd.getColumnName()).
                    append(" >= :").
                    append(fmd.getName()+"_after");
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


    /**
     * Определяет тип входных данных их фильтра @filter
     * @param fmd мета данные одного поля
     * @param params список параметров, которые вставляются в запрос при его отправке в бд
     * @param values массив значений фильтра
     * @param type тип пришедших данных для корректной вставки в фильтр
     */
    // Обдумай как красиво разделить данные по типу чтобы небыло тавталогии
    private static boolean setParams(FieldMetaData fmd, MapSqlParameterSource params, String[] values, Type type){
        switch(fmd.getType().getName()){
            case "java.lang.Long": {
                try {
                    //Если в массиве только одно значение и тип фильтра схожесть с конкретным значением
                    if(type == Type.LIKE){
                        Long val = Long.parseLong(values[0]);
                        params.addValue(fmd.getName(),val);
                    }
                    //Если в массиве множество значенийы и тип фильтра схожесть с конкретным значением
                    if(type == Type.LIST)
                    {
                        for (int i=0; i<values.length;i++){
                            Long val = Long.parseLong(values[i]);
                            params.addValue(fmd.getName()+i,val);
                        }
                    }
                    if(type == Type.BETWEEN){
                        Long val_from = Long.parseLong(values[0]);
                        Long val_to = Long.parseLong(values[1]);
                        params.addValue(fmd.getName()+"_from",val_from);
                        params.addValue(fmd.getName() + "_to", val_to);
                    }
                } catch (NumberFormatException e){
                    return false;
                }
                return true;
            }
            case "long": {
                try {
                    long val = Long.parseLong(values[0]);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;
            }

            case "java.lang.Integer":{
                try {
                    Integer val = Integer.valueOf(values[0]);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;

            }
            case "int": {
                try {
                    int val = Integer.valueOf(values[0]);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;
            }

            case "java.lang.String": {
                String val = "%"+values[0]+"%";
                params.addValue(fmd.getName(),val);
                return true;
            }

            case "java.lang.Boolean":{
                try {
                    Boolean val = Boolean.valueOf(values[0]);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;

            }
            case "boolean": {

                try {
                    boolean val = Boolean.parseBoolean(values[0]);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;
            }

            case "java.util.Date":{
                try {
                    if(type == Type.LIKE){
                        Long val = Long.parseLong(values[0]);
                        Timestamp date = new Timestamp(val);
                        params.addValue(fmd.getName(),date);
                    }
                    //Если в массиве множество значенийы и тип фильтра схожесть с конкретным значением
                    if(type == Type.LIST)
                    {
                        for (int i=0; i<values.length;i++){
                            Long val = Long.parseLong(values[i]);
                            Timestamp date = new Timestamp(val);
                            params.addValue(fmd.getName()+i,date);
                        }
                    }
                    if(type == Type.BETWEEN){
                        Long val_from = Long.parseLong(values[0]);
                        Timestamp date_from = new Timestamp(val_from);
                        Long val_to = Long.parseLong(values[1]);
                        Timestamp date_to = new Timestamp(val_to);
                        params.addValue(fmd.getName()+"_from",date_from);
                        params.addValue(fmd.getName() + "_to", date_to);
                    }
                    if(type == Type.BEFORE){
                        Long val = Long.parseLong(values[0]);
                        Timestamp date = new Timestamp(val);
                        params.addValue(fmd.getName()+"_before",date);
                    }
                    if(type == Type.AFTER){
                        Long val = Long.parseLong(values[0]);
                        Timestamp date = new Timestamp(val);
                        params.addValue(fmd.getName()+"_after",date);
                    }
                } catch (NumberFormatException e){
                    return false;
                }
                return true;
            }

            case "java.lang.Double":{
                if(values[0].indexOf(",")>-1){
                    values[0] = values[0].replace(",", ".");
                }
                try {
                    Double val = Double.valueOf(values[0]);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;
            }
            case "double":{
                try {
                    double val = Double.valueOf(values[0]);
                    params.addValue(fmd.getName(),val);
                } catch (NumberFormatException e){
                    return false;
                }
                return true;
            }

            case "ru.it.sd.model.Person":{
                try {
                    Long val = Long.parseLong(values[0]);
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
