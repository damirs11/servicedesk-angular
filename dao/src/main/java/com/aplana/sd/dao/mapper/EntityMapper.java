package com.aplana.sd.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

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

		return null;
	}

	/**
	 * Возвращает класс сущности, для которого был создан маппер
	 */
	public Class getGenericClass() {
		return (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

}