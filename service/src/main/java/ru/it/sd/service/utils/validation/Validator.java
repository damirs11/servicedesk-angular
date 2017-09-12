package ru.it.sd.service.utils.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.it.sd.exception.BadRequestException;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.meta.FieldMetaData;
import ru.it.sd.meta.MetaUtils;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Валидатор сущности по {@link FieldMetaData}
 *
 * @author NSychev
 * @since 11.09.2017
 */
public class Validator {

    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    /**
     * Валидация полей полученной сущности entity
     *
     * @param entity заполненная сущность которую будет проверять валидатор
     * @throws BadRequestException если хоть одно поле не соответсвует условиям {@link FieldMetaData}
     */
    public static void validate(Object entity){
        //FieldMetaData всех полей класса
        Map<String, FieldMetaData> fieldMetaDataList = MetaUtils.getFieldsMetaData(entity.getClass());
        //Список полей с ошибками
        List<String> errorFields = new ArrayList<>();
        //Цикл по всем полям сущности
        for (String key : fieldMetaDataList.keySet()) {
            //Получение FieldMeta конкретного поля
            FieldMetaData fmd = fieldMetaDataList.get(key);
            try {
                //Открываем поле, делаем его доступным, заполняем значение в value
                Field field = entity.getClass().getDeclaredField(key);
                field.setAccessible(true);
                Object value = field.get(entity);
                //Если поле объязательное
                if(fmd.isRequired()){
                    if(value == null){
                        errorFields.add(field.getName());
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                String msg = MessageFormat.format("Ошибка при получении значения для поля \"{0}\"", key);
                logger.error(msg, e);
                throw new ServiceException(msg, e);
            }
        }
        //Если есть объязательные поля но значения их равно null
        if(!errorFields.isEmpty()){
            throw new BadRequestException("Не заданы значения для обязательных полей: " +
                    StringUtils.join(errorFields, ", "));
        }
    }
}
