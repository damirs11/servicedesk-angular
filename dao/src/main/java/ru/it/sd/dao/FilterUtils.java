package ru.it.sd.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.collections.MultiMap;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import ru.it.sd.meta.FieldMetaData;
import ru.it.sd.meta.MetaUtils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.*;

/**
 * Статический класс добвления фильтров в запрос
 *
 * @author NSychev
 * @since 14.07.2017
 */
public class FilterUtils {
    //

    /**
     * Типы формирования условия
     * EQUAL - равенство
     * LIKE - схожесть
     * LIST - список значений
     * BETWEEN - между значениями
     * BEFORE - до определенного значения
     * AFTER - после определенного значения
     */
    private enum Type {EQUAL, LIKE, LIST, BETWEEN, BEFORE, AFTER};
    /**
     * Добовляет к запросу условия для данного класса по фильтру
     * @param queryPart запрос в который добавляются фильтры поиска (WHERE .... AND (.....) AND (....) OR (....))
     * @param params список параметров, которые вставляются в запрос при его отправке в бд
     * @param clazz модель по которой будет происходить поиск аннотированных полей
     * @param filter фильтр(Map<String, String>)
     * @param filterFields мапа в которой передается сет таблиц в качестве ключа и список полей этой таблицы для определения префкса таблицы при формировании запроса
     */
    public static void createFilter(StringBuilder queryPart, MapSqlParameterSource params, Map<String, String> filter, MultiMap filterFields, Class clazz){

        if((filter != null) && !filter.entrySet().isEmpty()) {
            // Временно !!!!
            queryPart.append("\n WHERE TRUE");

            //Поиск по всем полям класса
            List<FieldMetaData> fieldMetaDataList = MetaUtils.getFieldsMetaData(clazz);
            for (FieldMetaData fmd : fieldMetaDataList) {
                Type type = Type.EQUAL;
                //Если фильтр для данного поля существует key == field name и поиск по списку и одиночному значению
                if (filter.containsKey(fmd.getName())) {
                    //Разбиваем значение filter на массив данных
                    String[] valueArr = filter.get(fmd.getName()).split(";");
                    if(valueArr.length > 1) type = Type.LIST;
                    String prefix = getPrefix(filterFields,fmd);
                    //Отсеивание сложных объектов и приведение типов
                    if(prefix != null) {
                        if(checkParams(fmd, params, valueArr, type)) {
                        //Получение префикса таблицы по columnName в мапе filterFields
                            queryPart.append(getExpression(fmd,prefix,valueArr, type));
                        }
                    }
                }
                //Поиск похожих элементов
                if(filter.containsKey(fmd.getName()+"_like")){
                    type = Type.LIKE;
                    String[] valueArr = new String[1];
                    valueArr[0] = filter.get(fmd.getName()+"_like");

                    String prefix = getPrefix(filterFields,fmd);
                    //Отсеивание сложных объектов и приведение типов
                    if(prefix != null) {
                        if(checkParams(fmd, params, valueArr, type)) {
                            //Получение префикса таблицы по columnName в мапе filterFields
                            queryPart.append(getExpression(fmd,prefix,valueArr, type));
                        }
                    }
                }
                //Поиск до
                if(filter.containsKey(fmd.getName()+"_before")){
                    type = Type.BEFORE;
                    String[] valueArr = new String[1];
                    valueArr[0] = filter.get(fmd.getName()+"_before");

                    String prefix = getPrefix(filterFields,fmd);
                    //Отсеивание сложных объектов и приведение типов
                    if(prefix != null) {
                        if(checkParams(fmd, params, valueArr, type)) {
                            //Получение префикса таблицы по columnName в мапе filterFields
                            queryPart.append(getExpression(fmd,prefix,valueArr, type));
                        }
                    }
                }
                //Поиск после
                if(filter.containsKey(fmd.getName()+"_after")){
                    type = Type.AFTER;
                    String[] valueArr = new String[1];
                    valueArr[0] = filter.get(fmd.getName()+"_after");

                    String prefix = getPrefix(filterFields,fmd);
                    //Отсеивание сложных объектов и приведение типов
                    if(prefix != null) {
                        if(checkParams(fmd, params, valueArr, type)) {
                            //Получение префикса таблицы по columnName в мапе filterFields
                            queryPart.append(getExpression(fmd,prefix,valueArr, type));
                        }
                    }
                }
                //Поиск между
                if(filter.containsKey(fmd.getName()+"_between")){
                    type = Type.BETWEEN;
                    String[] valueArr = filter.get(fmd.getName()+"_between").split(":");

                    String prefix = getPrefix(filterFields,fmd);
                    //Отсеивание сложных объектов и приведение типов
                    if(prefix != null) {
                        if(checkParams(fmd, params, valueArr, type)) {
                            //Получение префикса таблицы по columnName в мапе filterFields
                            queryPart.append(getExpression(fmd,prefix,valueArr, type));
                        }
                    }

                }
            }
        }
    }

    /**
     * Формирует строку условия (AND 'table_prefix'.'column_name' = 'value')
     * @param fmd мета данные поля полуемые из {@link MetaUtils#getFieldsMetaData(Class)}
     * @param prefix префикс таблицы, определяется в функции {@link #getPrefix(MultiMap, FieldMetaData)}
     * @param values значение(я) фильтра для конкретного поля
     * @param type тип поиска, все варианты поиска перечислены в {@link Type}
     * @return строку с условием
     */
    private static String getExpression(FieldMetaData fmd, String prefix, String[]values,  Type type){
        StringBuilder expression = new StringBuilder();
        //Формирование условия для равенства значения
        if(type == Type.EQUAL){
            expression.
                    append("\n\tAND ").
                    append(prefix).
                    append(".").
                    append(fmd.getColumnName()).
                    append(" = :").
                    append(fmd.getName()).
                    append("");
            return expression.toString();
        }

        //Формирование условия для схожести значения
        if(type == Type.LIKE){
            expression.
                    append("\n\tAND ").
                    append(prefix).
                    append(".").
                    append(fmd.getColumnName()).
                    append(" LIKE '%'||:").
                    append(fmd.getName()).append("_like").
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
                        append(fmd.getName()).append(i);
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
                    append(fmd.getName()).append("_from").
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
                    append(fmd.getName()).append("_before");
            return expression.toString();
        }
        if(type == Type.AFTER){
            expression.
                    append("\n\tAND ").
                    append(prefix).
                    append(".").
                    append(fmd.getColumnName()).
                    append(" >= :").
                    append(fmd.getName()).append("_after");
            return expression.toString();
        }
        return "";
    }

    /**
     * Функция проверяет входит ли поле fmd в filterFields, если нет то данное поле будет исключено из фильтра
     * @param filterFields мапа в которой передается сет таблиц в качестве ключа и список полей этой таблицы для определения префкса таблицы при формировании запроса
     * @param fmd мета данные поля полуемые из {@link MetaUtils#getFieldsMetaData(Class)}
     * @return
     */
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
     * @param fmd мета данные поля полуемые из {@link MetaUtils#getFieldsMetaData(Class)}
     * @param params список параметров, которые вставляются в запрос при его отправке в бд
     * @param values массив значений фильтра
     * @param type тип поиска, все варианты поиска перечислены в {@link Type}
     * @return истина если нашлось поле для ключа из filtr и не было ошибок при его преобразовании ; ложль если не нашлось подходящего поля в классе и
     */
    private static boolean checkParams(FieldMetaData fmd, MapSqlParameterSource params, String[] values, Type type){
        switch(fmd.getType().getName()){
            case "java.lang.Long": {
                try {
                   setNumberParams(fmd,params,values,type,Long.class);
                } catch (Exception e){
                    return false;
                }
                return true;
            }
            case "long": {
                try {
                    setNumberParams(fmd,params,values,type,Long.class);
                } catch (Exception e){
                    return false;
                }
                return true;
            }

            case "java.lang.Integer":{
                try {
                    setNumberParams(fmd,params,values,type,Integer.class);
                } catch (Exception e){
                    return false;
                }
                return true;

            }
            case "int": {
                try {
                    setNumberParams(fmd,params,values,type,Integer.class);
                } catch (Exception e){
                    return false;
                }
                return true;
            }

            case "java.lang.String": {
                //по конкретному значению
                if(type == Type.EQUAL){
                    String val = values[0];
                    params.addValue(fmd.getName(),val);
                }
                //Если в массиве только одно значение и тип фильтра схожесть с конкретным значением
                if(type == Type.LIKE){
                    String val = values[0];
                    params.addValue(fmd.getName()+"_like",val);
                }
                //Если в массиве множество значенийы и тип фильтра схожесть с конкретным значением
                if(type == Type.LIST)
                {
                    for (int i=0; i<values.length;i++){
                        String val = values[i];
                        params.addValue(fmd.getName()+i,val);
                    }
                }
                return true;
            }

            case "java.lang.Boolean":{
                try {
                    setNumberParams(fmd,params,values,type,Boolean.class);
                } catch (Exception e){
                    return false;
                }
                return true;

            }
            case "boolean": {

                try {
                    setNumberParams(fmd,params,values,type,Boolean.class);
                } catch (Exception e){
                    return false;
                }
                return true;
            }

            case "java.util.Date":{
                try {
                    if(type == Type.EQUAL){
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
                try {
                    setNumberParams(fmd,params,values,type,Double.class);
                } catch (Exception e){
                    return false;
                }
                return true;
            }
            case "double":{
                try {
                    setNumberParams(fmd,params,values,type,Double.class);
                } catch (Exception e){
                    return false;
                }
                return true;
            }

            case "ru.it.sd.model.Person":{
                try {
                    setNumberParams(fmd,params,values,type,Long.class);
                } catch (Exception e){
                    return false;
                }
                return true;
            }
            case "ru.it.sd.model.EntityStatus":{
                try {
                    setNumberParams(fmd,params,values,type,Long.class);
                } catch (Exception e){
                    return false;
                }
                return true;
            }
            case "ru.it.sd.model.EntityCategory":{
                try {
                    setNumberParams(fmd,params,values,type,Long.class);
                } catch (Exception e){
                    return false;
                }
                return true;
            }
            case "ru.it.sd.model.Organization":{
                try {
                    setNumberParams(fmd,params,values,type,Long.class);
                } catch (Exception e){
                    return false;
                }
                return true;
            }
            case "ru.it.sd.model.Brand":{
                try {
                    setNumberParams(fmd,params,values,type,Long.class);
                } catch (Exception e){
                    return false;
                }
                return true;
            }
            case "ru.it.sd.model.Folder":{
                try {
                    setNumberParams(fmd,params,values,type,Long.class);
                } catch (Exception e){
                    return false;
                }
                return true;
            }
            case "ru.it.sd.model.Location":{
                try {
                    setNumberParams(fmd,params,values,type,Long.class);
                } catch (Exception e){
                    return false;
                }
                return true;
            }
        }

        return false;
    }

    /**
     * Метод для минимизации функции {@link #checkParams(FieldMetaData, MapSqlParameterSource, String[], Type)}
     * Все типы данных наследуемые от Number имеют функцию valueOf которое возвращает значение в clazz формате
     * Содержит разбиение по типам фильтра {@link Type}
     * А также добавляет полченные параметры в params
     * @param fmd мета данные поля полуемые из {@link MetaUtils#getFieldsMetaData(Class)}
     * @param params список параметров, которые вставляются в запрос при его отправке в бд
     * @param values массив значений фильтра
     * @param type тип поиска, все варианты поиска перечислены в {@link Type}
     * @param clazz класс из которого вызывается функция valueOf
     */
    private static void setNumberParams (FieldMetaData fmd, MapSqlParameterSource params, String[] values, Type type,Class clazz) throws InvocationTargetException, IllegalAccessException {

        Method valueOf;
        try {
            valueOf = clazz.getMethod("valueOf", String.class);
        } catch (NoSuchMethodException e) {
            return;
        }

        //по конкретному значению
        if(type == Type.EQUAL){
            Object val = valueOf.invoke(clazz, values[0]);
            params.addValue(fmd.getName(),val);
        }
        //Если в массиве только одно значение и тип фильтра схожесть с конкретным значением
        if(type == Type.LIKE){
            Object val = valueOf.invoke(clazz,values[0]);
            params.addValue(fmd.getName()+"_like",val);
        }
        //Если в массиве множество значенийы и тип фильтра схожесть с конкретным значением
        if(type == Type.LIST)
        {
            for (int i=0; i<values.length;i++){
                Object val = valueOf.invoke(clazz,values[i]);
                params.addValue(fmd.getName()+i,val);
            }
        }
        if(type == Type.BETWEEN){
            Object val_from = valueOf.invoke(clazz,values[0]);
            Object val_to = valueOf.invoke(clazz,values[1]);
            params.addValue(fmd.getName()+"_from",val_from);
            params.addValue(fmd.getName() + "_to", val_to);
        }
        if(type == Type.BEFORE){
            Object val = valueOf.invoke(clazz,values[0]);
            params.addValue(fmd.getName()+"_before",val);
        }
        if(type == Type.AFTER){
            Object val = valueOf.invoke(clazz,values[0]);
            params.addValue(fmd.getName()+"_after",val);
        }
    }
}