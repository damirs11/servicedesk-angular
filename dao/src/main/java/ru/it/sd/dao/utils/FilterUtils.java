package ru.it.sd.dao.utils;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.meta.FieldMetaData;
import ru.it.sd.meta.MetaUtils;

import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Map;

/**
 * Статический класс добвления фильтров в запрос
 *
 * @author NSychev
 * @since 14.07.2017
 */
public class FilterUtils {
    //

    /**
     * Возможные сравнения
     * EQUAL - равенство
     * LIKE - схожесть
     * LIST - список значений
     * BETWEEN - между значениями
     * BEFORE - до определенного значения
     * AFTER - после определенного значения
     */
    private enum Comparison {
        EQUAL, LIKE, LIST, BETWEEN, BEFORE, AFTER
    }

    /**
     * Добавляет к запросу условия для данного класса по фильтру
     *
     * @param queryPart    строка в которую добавляются фильтры поиска (WHERE .... AND (.....) AND (....) OR (....))
     * @param params       список параметров, которые вставляются в запрос при его отправке в бд
     * @param clazz        модель по которой будет происходить поиск аннотированных полей
     * @param filter       фильтр(Map<String, String>)
     */
    public static void createFilter(StringBuilder queryPart, MapSqlParameterSource params, Map<String, String> filter,  Class clazz) {
        if (MapUtils.isNotEmpty(filter)) {
            queryPart.append(" WHERE 1 = 1"); // "WHERE TRUE" в MS SQL не работает
            //Поиск по всем полям класса
            Map<String, FieldMetaData> fieldMetaDataList = MetaUtils.getFieldsMetaData(clazz);
            for (FieldMetaData fmd : fieldMetaDataList.values()) {
                if(fmd.isAnnotation()) {
                    String prefix = getPrefix(clazz, fmd);
                    Comparison type;
                    //Сравнение одиночных значений и списка на равенство(Comprasion.EQUAl)
                    if (filter.containsKey(fmd.getName())) {
                        String value = filter.get(fmd.getName());
                        equalComparison(queryPart, fmd, params, value, prefix);
                        continue;
                    }
                    //Поиск похожих элементов
                    if (filter.containsKey(fmd.getName() + "_like")) {
                        String value = filter.get(fmd.getName()+"_like");
                        likeComparison(queryPart, fmd, params, value, prefix);
                        continue;
                    }
                    //Поиск до
                    if (filter.containsKey(fmd.getName() + "_before")) {
                        String value = filter.get(fmd.getName()+"_before");
                        beforeComparison(queryPart, fmd, params, value, prefix);
                        continue;
                    }
                    //Поиск после
                    if (filter.containsKey(fmd.getName() + "_after")) {
                        String value = filter.get(fmd.getName()+"_after");
                        afterComparison(queryPart, fmd, params, value, prefix);
                        continue;
                    }
                    //Поиск между
                    if (filter.containsKey(fmd.getName() + "_between")) {
                        type = Comparison.BETWEEN;
                        String value = filter.get(fmd.getName() + "_between");
                        betweenComparison(queryPart, fmd, params, value, prefix);
                        continue;
                    }
                }

            }
        }
    }

    /**
     * Функция добовляющая условие на равенство одиночных значений и списка значений
     *
     * @param fmd    мета данные поля полуемые из {@link MetaUtils#getFieldsMetaData(Class)}
     * @param prefix префикс таблицы, определяется в функции {@link #getPrefix}
     * @param value значение(я) фильтра для конкретного поля
     * @param params  список параметров, которые вставляются в запрос при его отправке в бд
     */
    private static void equalComparison(StringBuilder queryPart, FieldMetaData fmd,MapSqlParameterSource params, String value, String prefix){
        Comparison type = Comparison.EQUAL;
        //Разбиваем значение filter на массив данных
        String[] valueArr = value.split(";");
        //Если в массиве больше 1 элемента значит это список значений
        if (valueArr.length > 1) type = Comparison.LIST;
        //Определение типа данных по полю fmd
        if (checkParams(fmd, params, valueArr, type)) {
            if(type == Comparison.EQUAL){
            queryPart.
                    append("\n\tAND ").
                    append(prefix).
                    append(StringUtils.isNotBlank(prefix) ? "." : "").
                    append(fmd.getColumnName()).
                    append(" = :").
                    append(fmd.getName()).
                    append("");
            return;
            } else {
                queryPart.append("\n\tAND (");
                for (int i = 0; i < valueArr.length; i++) {
                    queryPart.
                            append(prefix).
                            append(StringUtils.isNotBlank(prefix) ? "." : "").
                            append(fmd.getColumnName()).
                            append(" = :").
                            append(fmd.getName()).append(i);
                    if (i != valueArr.length - 1) queryPart.append(" OR ");
                }
                queryPart.append(")");
            }
        }
    }

    /**
     * Функция добовляющая условие на схожесть значений
     *
     * @param fmd    мета данные поля полуемые из {@link MetaUtils#getFieldsMetaData(Class)}
     * @param prefix префикс таблицы, определяется в функции {@link #getPrefix}
     * @param value значение(я) фильтра для конкретного поля
     * @param params  список параметров, которые вставляются в запрос при его отправке в бд
     * @return строку с условием
     */
    private static void likeComparison(StringBuilder queryPart, FieldMetaData fmd,MapSqlParameterSource params, String value, String prefix){
        String[] valueArr = new String[1];
        valueArr[0] = value;

        //Определение типа данных по полю fmd
        if (checkParams(fmd, params, valueArr, Comparison.LIKE)) {
            queryPart.
                    append("\n\tAND ").
                    append(prefix).
                    append(StringUtils.isNotBlank(prefix) ? "." : "").
                    append(fmd.getColumnName()).
                    append(" LIKE '%'||:").
                    append(fmd.getName()).append("_like").
                    append("||'%'");
        }
    }

    /**
     * Функция добовляющая условие меньше конкретного значения (<=10)
     *
     * @param fmd    мета данные поля полуемые из {@link MetaUtils#getFieldsMetaData(Class)}
     * @param prefix префикс таблицы, определяется в функции {@link #getPrefix}
     * @param value значение(я) фильтра для конкретного поля
     * @param params  список параметров, которые вставляются в запрос при его отправке в бд
     * @return строку с условием
     */
    private static void beforeComparison(StringBuilder queryPart, FieldMetaData fmd,MapSqlParameterSource params, String value, String prefix){
        String[] valueArr = new String[1];
        valueArr[0] = value;

        //Определение типа данных по полю fmd
        if (checkParams(fmd, params, valueArr, Comparison.BEFORE)) {
            queryPart.
                    append("\n\tAND ").
                    append(prefix).
                    append(StringUtils.isNotBlank(prefix) ? "." : "").
                    append(fmd.getColumnName()).
                    append(" <= :").
                    append(fmd.getName()).append("_before");
        }
    }

    /**
     * Функция добовляющая условие больше конкретного значения (<=10)
     *
     * @param fmd    мета данные поля полуемые из {@link MetaUtils#getFieldsMetaData(Class)}
     * @param prefix префикс таблицы, определяется в функции {@link #getPrefix}
     * @param value значение(я) фильтра для конкретного поля
     * @param params  список параметров, которые вставляются в запрос при его отправке в бд
     * @return строку с условием
     */
    private static void afterComparison(StringBuilder queryPart, FieldMetaData fmd,MapSqlParameterSource params, String value, String prefix){
        String[] valueArr = new String[1];
        valueArr[0] = value;

        //Определение типа данных по полю fmd
        if (checkParams(fmd, params, valueArr, Comparison.AFTER)) {
            queryPart.
                    append("\n\tAND ").
                    append(prefix).
                    append(StringUtils.isNotBlank(prefix) ? "." : "").
                    append(fmd.getColumnName()).
                    append(" >= :").
                    append(fmd.getName()).append("_after");
        }
    }

    /**
     * Функция добовляющая условие для промежутка значений (1:10)
     *
     * @param fmd    мета данные поля полуемые из {@link MetaUtils#getFieldsMetaData(Class)}
     * @param prefix префикс таблицы, определяется в функции {@link #getPrefix}
     * @param value значение(я) фильтра для конкретного поля
     * @param params  список параметров, которые вставляются в запрос при его отправке в бд
     * @return строку с условием
     */
    private static void betweenComparison(StringBuilder queryPart, FieldMetaData fmd,MapSqlParameterSource params, String value, String prefix){
        String[] valueArr = value.split(":");

        //Определение типа данных по полю fmd
        if (checkParams(fmd, params, valueArr, Comparison.BETWEEN)) {
            queryPart.
                    append("\n\tAND ").
                    append(prefix).
                    append(StringUtils.isNotBlank(prefix) ? "." : "").
                    append(fmd.getColumnName()).
                    append(" BETWEEN :").
                    append(fmd.getName()).append("_from").
                    append(" AND :").
                    append(fmd.getName() + "_to");
        }
    }

    /**
     * Получить алиас таблицы для поля
     *
     * @param clazz класс объекта для получения данных из аннотации класа {@link ClassMeta#tableAlias()}
     * @param fmd мета данные поля полуемые из {@link MetaUtils#getFieldsMetaData(Class)}, для получения данных из {@link FieldMeta#tableAlias()}
     * @return алиас таблицы
     */
    private static String getPrefix(@NotNull Class clazz, @NotNull FieldMetaData fmd) {
        if(StringUtils.isNotBlank(fmd.getTableAlias())) {
            return fmd.getTableAlias();
        }
        ClassMeta classMeta = (ClassMeta)clazz.getAnnotation(ClassMeta.class);
        //CheckUtils.requireNotNull(classMeta, ResourceMessages.getMessage("error.table.alias.not.found"));
        return classMeta == null ? "" : classMeta.tableAlias();
    }


    /**
     * Определяет тип входных данных их фильтра @filter
     *
     * @param fmd    мета данные поля полуемые из {@link MetaUtils#getFieldsMetaData(Class)}
     * @param params список параметров, которые вставляются в запрос при его отправке в бд
     * @param values массив значений фильтра
     * @param type   тип поиска, все варианты поиска перечислены в {@link Comparison}
     * @return истина если нашлось поле для ключа из filtr и не было ошибок при его преобразовании ; ложль если не нашлось подходящего поля в классе и
     */
    private static boolean checkParams(FieldMetaData fmd, MapSqlParameterSource params, String[] values, Comparison type) {
        switch (fmd.getType().getName()) {
            case "java.lang.Long": {
                try {
                    setNumberParams(fmd, params, values, type, Long.class);
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
            case "long": {
                try {
                    setNumberParams(fmd, params, values, type, Long.class);
                } catch (Exception e) {
                    return false;
                }
                return true;
            }

            case "java.lang.Integer": {
                try {
                    setNumberParams(fmd, params, values, type, Integer.class);
                } catch (Exception e) {
                    return false;
                }
                return true;

            }
            case "int": {
                try {
                    setNumberParams(fmd, params, values, type, Integer.class);
                } catch (Exception e) {
                    return false;
                }
                return true;
            }

            case "java.lang.String": {
                //по конкретному значению
                if (type == Comparison.EQUAL) {
                    String val = values[0];
                    params.addValue(fmd.getName(), val);
                }
                //Если в массиве только одно значение и тип фильтра схожесть с конкретным значением
                if (type == Comparison.LIKE) {
                    String val = values[0];
                    params.addValue(fmd.getName() + "_like", val);
                }
                //Если в массиве множество значенийы и тип фильтра схожесть с конкретным значением
                if (type == Comparison.LIST) {
                    for (int i = 0; i < values.length; i++) {
                        String val = values[i];
                        params.addValue(fmd.getName() + i, val);
                    }
                }
                return true;
            }

            case "java.lang.Boolean": {
                try {
                    setNumberParams(fmd, params, values, type, Boolean.class);
                } catch (Exception e) {
                    return false;
                }
                return true;

            }
            case "boolean": {

                try {
                    setNumberParams(fmd, params, values, type, Boolean.class);
                } catch (Exception e) {
                    return false;
                }
                return true;
            }

            case "java.util.Date": {
                try {
                    if (type == Comparison.EQUAL) {
                        Long val = Long.parseLong(values[0]);
                        Timestamp date = new Timestamp(val);
                        params.addValue(fmd.getName(), date);
                    }
                    //Если в массиве множество значенийы и тип фильтра схожесть с конкретным значением
                    if (type == Comparison.LIST) {
                        for (int i = 0; i < values.length; i++) {
                            Long val = Long.parseLong(values[i]);
                            Timestamp date = new Timestamp(val);
                            params.addValue(fmd.getName() + i, date);
                        }
                    }
                    if (type == Comparison.BETWEEN) {
                        Long val_from = Long.parseLong(values[0]);
                        Timestamp date_from = new Timestamp(val_from);
                        Long val_to = Long.parseLong(values[1]);
                        Timestamp date_to = new Timestamp(val_to);
                        params.addValue(fmd.getName() + "_from", date_from);
                        params.addValue(fmd.getName() + "_to", date_to);
                    }
                    if (type == Comparison.BEFORE) {
                        Long val = Long.parseLong(values[0]);
                        Timestamp date = new Timestamp(val);
                        params.addValue(fmd.getName() + "_before", date);
                    }
                    if (type == Comparison.AFTER) {
                        Long val = Long.parseLong(values[0]);
                        Timestamp date = new Timestamp(val);
                        params.addValue(fmd.getName() + "_after", date);
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
                return true;
            }

            case "java.lang.Double": {
                try {
                    setNumberParams(fmd, params, values, type, Double.class);
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
            case "double": {
                try {
                    setNumberParams(fmd, params, values, type, Double.class);
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
            //Для всех остальных классов
            default:{
                try {
                    setNumberParams(fmd, params, values, type, Long.class);
                } catch (Exception e) {
                    return false;
                }
                return true;
            }

        }
    }

    /**
     * Метод для минимизации функции {@link #checkParams(FieldMetaData, MapSqlParameterSource, String[], Comparison)}
     * Все типы данных наследуемые от Number имеют функцию valueOf которое возвращает значение в clazz формате
     * Содержит разбиение по типам фильтра {@link Comparison}
     * А также добавляет полченные параметры в params
     *
     * @param fmd    мета данные поля полуемые из {@link MetaUtils#getFieldsMetaData(Class)}
     * @param params список параметров, которые вставляются в запрос при его отправке в бд
     * @param values массив значений фильтра
     * @param type   тип поиска, все варианты поиска перечислены в {@link Comparison}
     * @param clazz  класс из которого вызывается функция valueOf
     */
    private static void setNumberParams(FieldMetaData fmd, MapSqlParameterSource params, String[] values, Comparison type, Class clazz) throws InvocationTargetException, IllegalAccessException {

        Method valueOf;
        try {
            valueOf = clazz.getMethod("valueOf", String.class);
        } catch (NoSuchMethodException e) {
            return;
        }

        //по конкретному значению
        if (type == Comparison.EQUAL) {
            Object val = valueOf.invoke(clazz, values[0]);
            params.addValue(fmd.getName(), val);
        }
        //Если в массиве только одно значение и тип фильтра схожесть с конкретным значением
        if (type == Comparison.LIKE) {
            Object val = valueOf.invoke(clazz, values[0]);
            params.addValue(fmd.getName() + "_like", val);
        }
        //Если в массиве множество значенийы и тип фильтра схожесть с конкретным значением
        if (type == Comparison.LIST) {
            for (int i = 0; i < values.length; i++) {
                Object val = valueOf.invoke(clazz, values[i]);
                params.addValue(fmd.getName() + i, val);
            }
        }
        if (type == Comparison.BETWEEN) {
            Object val_from = valueOf.invoke(clazz, values[0]);
            Object val_to = valueOf.invoke(clazz, values[1]);
            params.addValue(fmd.getName() + "_from", val_from);
            params.addValue(fmd.getName() + "_to", val_to);
        }
        if (type == Comparison.BEFORE) {
            Object val = valueOf.invoke(clazz, values[0]);
            params.addValue(fmd.getName() + "_before", val);
        }
        if (type == Comparison.AFTER) {
            Object val = valueOf.invoke(clazz, values[0]);
            params.addValue(fmd.getName() + "_after", val);
        }
    }

    /**
     * Возвращает значение булевского флага
     *
     * @param flag строкое значение флага, которое необходимо преобразовать в буль
     * @return true - если значение задано и отличается от "false" и "0". Иначе false
     */
    public static boolean getFlagValue(String flag) {
        return StringUtils.isNotBlank(flag) && !("false".equalsIgnoreCase(flag) || "0".equalsIgnoreCase(flag));
    }
}