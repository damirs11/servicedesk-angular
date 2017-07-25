package ru.it.sd.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import ru.it.sd.meta.FieldMetaData;
import ru.it.sd.meta.MetaUtils;
import ru.it.sd.model.PagingRange;
import ru.it.sd.model.SortingInfo;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Абстрактный класс для работы с сущностями
 *
 * @author quadrix
 * @since 24.07.2017
 */
public abstract class AbstractEntityDao extends AbstractDao {

	/**
	 * Удаляет из фильтра параметры для постраничного просмотра и сортироваки
	 * @param filter исходный фильтр
	 * @return фильтр без "лишних" условий
	 */
	protected Map<String, String> prepareFilterForCount(Map<String, String> filter) {
		Map<String, String> filterCount = new HashMap<>(filter);
		filterCount.remove(PagingRange.PAGING_PARAM_NAME);
		filterCount.remove(SortingInfo.SORTING_PARAM_NAME);
		return filter;
	}

	/**
	 * Обрабатывает условия постраничного отображения данных, если они были указаны
	 * @param filter исходный фильтр, в котором выполняется поиск параметров пейджинга
	 * @param sql базовый запрос на получение данных, который будет оборачиваться фильтрацией по страницам
	 * @param params в данный аргмент будут записаны значения ограничений текущей страницы
	 */
	protected void buildPaging(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		PagingRange paging = PagingRange.fromFilter(filter);
		if (paging != null) {
			params.addValue("paging_from", paging.getFrom());
			params.addValue("paging_to", paging.getTo());

			StringBuilder sb = new StringBuilder();
			sql.insert(0,"WITH paging AS (\n");
			sql.append("\n)SELECT * FROM paging\n");
			sql.append("WHERE RowNum >= :paging_from AND RowNum <= :paging_to");
		}
	}

	protected void buildOrderBy(Map<String, String> filter, StringBuilder sql, Class clazz) {
		String rowNumSql = null;
		SortingInfo sort = SortingInfo.fromFilter(filter);
		if (sort != null) {
			rowNumSql = getSortableColumnNames(sort, clazz); // сортируем записи по явно указанным столбцам таблицы
		} else if (filter.containsKey(PagingRange.PAGING_PARAM_NAME)) {
			rowNumSql = getKeyColumnNames(clazz); // сортируем по ключевым столбцам
		}
		if (rowNumSql != null) {
			StringBuilder sb = new StringBuilder(" ROW_NUMBER() OVER (ORDER BY ");
			sb.append(rowNumSql);
			sb.append(") AS RowNum,\n");
			int pos = sql.indexOf("SELECT");
			sql.insert(pos + "SELECT".length(), sb.toString());
		}
	}

	/**
	 * Возвращает список сортируемых столбцов в sql-виде: "column1 DESC, column2, column3 DESC, ...\n"
	 */
	private String getSortableColumnNames(@NotNull SortingInfo sort, @NotNull Class clazz) {
		StringBuilder sb = new StringBuilder();
		Map<String, FieldMetaData> meta = MetaUtils.getFieldsMetaData(clazz);
		for (String fieldName : sort.getColumns().keySet()) { // по всем столбцам, которые пользователь хочет отсортировать
			FieldMetaData fieldMeta = meta.get(fieldName);
			if (fieldMeta != null && fieldMeta.getColumnName() != null) { // обнаружили, что для этого столбца указано название столбца в бд
				if (StringUtils.isNotBlank(fieldMeta.getTableAlias())) {
					sb.append(fieldMeta.getTableAlias()); // добавляем алиас таблицы
					sb.append('.');
				}
				sb.append(fieldMeta.getColumnName());
				if (!sort.getColumns().get(fieldName)) { // указываем направление сортировки
					sb.append(" DESC");
				}
				sb.append(", ");
			}
		}
		if (sb.length() > 0) {
			sb.substring(0, sb.length() - 2); // обрезаем последнюю запятую
			sb.append('\n');
		}
		return sb.toString();
	}

	/**
	 * Возвращает список ключевых столбцов в sql-виде: "id DESC, code, ...\n"
	 */
	private String getKeyColumnNames(@NotNull Class clazz) {
		StringBuilder sb = new StringBuilder();
		Map<String, FieldMetaData> meta = MetaUtils.getKeyProperties(clazz);
		for (FieldMetaData fieldMeta : meta.values()) {
			if (fieldMeta != null && fieldMeta.getColumnName() != null) { // обнаружили, что для этого столбца указано название столбца в бд
				if (StringUtils.isNotBlank(fieldMeta.getTableAlias())) {
					sb.append(fieldMeta.getTableAlias()); // добавляем алиас таблицы
					sb.append('.');
				}
				sb.append(fieldMeta.getColumnName());
				sb.append(", ");
			}
		}
		if (sb.length() > 0) {
			return sb.substring(0, sb.length() - 2); // обрезаем последнюю запятую
		}
		throw new IllegalArgumentException("У сущности должен быть указан хотя бы один ключевой столбец");
	}
}
