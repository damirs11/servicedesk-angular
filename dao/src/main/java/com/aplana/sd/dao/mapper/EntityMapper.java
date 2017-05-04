package com.aplana.sd.dao.mapper;

import com.aplana.sd.exception.AppException;
import com.aplana.sd.meta.FieldMetaData;
import com.aplana.sd.meta.MetaUtils;
import com.aplana.sd.util.ResourceMessages;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Базовый класс для маппинга DAO. Два интерфейса {@see ResultSetExtractor} и
 * {@see RowMapper} объединены для удобства, чтобы не плодить дополнительные классы.
 * Один класс, который используется везде и позволяет оптимизировать работу
 * с данными в особых, чувствительных к скорости, методах.
 *
 * @author quadrix
 * @since 03.05.2017
 */
public class EntityMapper<T> implements ResultSetExtractor<List<T>>, RowMapper<T> {

	private static final Logger LOG = LoggerFactory.getLogger(EntityMapper.class);
	@Override
	public List<T> extractData(ResultSet rs) throws SQLException, DataAccessException {
		// Здесь можно создать кэши для оптимизации производительности. Например, когда
		// мы говорим про сложные ветвистые классы со множеством ссылок на другие классы.
		// По умолчанию, идет обычный вызов mapRow()
		List<T> list = new ArrayList<>();
		int i = 0;
		while(rs.next()){
			list.add(mapRow(rs, i++));
		}
		return list;
	}

	/**
	 * Дополнительный метод для работы с целыми числами, включая проверку на NULL.
	 * Отличие от обычного метода {@code java.sql.ResultSet#getLong(java.lang.String)}
	 * заключается в том, что при отсутствии значения возращается не 0, а NULL.
	 *
	 * @param rs
	 * @param columnName
	 * @return
	 * @throws SQLException
	 */
	protected Long getLong(ResultSet rs, String columnName) throws SQLException {
		Long value = rs.getLong(columnName);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		Class clazz = getGenericClass();
		// Создаем объект класса
		T result;
		try {
			result = (T) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new AppException(ResourceMessages.getMessage("error.class.instance.create", clazz.getName()));
		}
		// Выставляем значения полей объекта
		List<FieldMetaData> fmds = MetaUtils.getFieldsMetaData(clazz);
		for (FieldMetaData fmd : fmds) {
			String columnName = fmd.getColumnName();
			// Обрабатываем только поля, для которых указано соответствие столбца таблицы в бд.
			// Все остальные считаем служебными, либо отдаем на обработку классам-наследникам.
			if (StringUtils.isNotBlank(columnName)) {
				Field field = ReflectionUtils.findField(clazz, fmd.getName());
				field.setAccessible(true);
				switch(fmd.getType().getName()){
					case "java.lang.Long": {
						long value = rs.getLong(columnName);
						ReflectionUtils.setField(field, result, rs.wasNull() ? null : value);
					}
					case "long": {
						ReflectionUtils.setField(field, result, rs.getLong(columnName));
					}
					break;
					case "java.lang.Integer":{
						int value = rs.getInt(columnName);
						ReflectionUtils.setField(field, result, rs.wasNull() ? null : value);
					}
					case "int": {
						ReflectionUtils.setField(field, result, rs.getInt(columnName));
					}
					break;
					case "java.lang.String": {
						ReflectionUtils.setField(field, result, rs.getString(columnName));
					}
					break;
					case "java.lang.Boolean":{
						boolean value = rs.getBoolean(columnName);
						ReflectionUtils.setField(field, result, rs.wasNull() ? null : value);
					}
					case "boolean": {
						ReflectionUtils.setField(field, result, rs.getBoolean(columnName));
					}
					break;
					case "java.util.Date":{
						ReflectionUtils.setField(field, result, rs.getTimestamp(columnName));
					}
					case "java.lang.Double":{
						double value = rs.getDouble(columnName);
						ReflectionUtils.setField(field, result, rs.wasNull() ? null : value);
					}
					case "double":{
						ReflectionUtils.setField(field, result, rs.getDouble(columnName));
					}
				}

			}
		}
		return result;
	}

	/**
	 * Возвращает класс сущности, для которого был создан маппер
	 */
	protected Class getGenericClass() {
		return (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

}