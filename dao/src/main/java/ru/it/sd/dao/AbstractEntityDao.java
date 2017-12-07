package ru.it.sd.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.dao.utils.FilterUtils;
import ru.it.sd.meta.FieldMetaData;
import ru.it.sd.meta.MetaUtils;
import ru.it.sd.model.HasId;
import ru.it.sd.model.PagingRange;
import ru.it.sd.model.SortingInfo;
import ru.it.sd.util.EntityUtils;
import ru.it.sd.util.ResourceMessages;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Абстрактный класс для работы с сущностями
 *
 * @author quadrix
 * @since 24.07.2017
 */
public abstract class AbstractEntityDao<EntityClass extends HasId> extends AbstractDao {

	@Autowired
	protected DBUtils dbUtils;

	@SuppressWarnings("unchecked")
	protected Class<EntityClass> entityClass = EntityUtils.getGenericClass(getClass());

	/**
	 * Возвращает базовый запрос для выборки данных сущности
	 */
	protected abstract StringBuilder getBaseSql();

	/**
	 * Возвращает список сущностей по фильтру
	 *
	 * @param filter условия отбора записей
	 * @return список сущностей
	 */
	public List<EntityClass> list(Map<String, String> filter) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder sql = getBaseSql();
		if (filter != null) {
			buildWhere(filter, sql, params);
			buildOrderBy(filter, sql, "SELECT ".length());
			buildPaging(filter, sql, params);
		}
		return executeQuery(sql.toString(), params);
	}

	/**
	 * Вовзвращает общее количество записей по фильтру
	 *
	 * @param filter условие отбора записей
	 * @return количество найденных записей
	 */
	public int count(Map<String, String> filter) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder sql = getBaseSql();
		if (filter != null) {
			buildWhere(clearFilter(filter), sql, params);
		}
		sql.insert(0, "SELECT COUNT(*) FROM (\n");
		sql.append(") t");
		return namedJdbc.queryForObject(sql.toString(), params, int.class);
	}

	/**
	 * Удаляет из фильтра сортировку и пейджинг
	 */
	public static Map<String, String> clearFilter(Map<String, String> filter) {
		if (filter == null) {
			return null;
		}
		Map<String, String> result = new HashMap<>(filter);
		result.remove(PagingRange.PAGING_PARAM_NAME);
		result.remove(SortingInfo.SORTING_PARAM_NAME);
		return result;
	}

	/**
	 * Получает сущность по её идентификатору
	 *
	 * @param id идентификатор сущности. Может быть null
	 * @return сущность. Если идентификатор не задан, либо искомой сущности в бд нет, то вернется null
	 */
	@Cacheable(cacheNames = "entity", key = "#root.targetClass.getSimpleName() + #id")
	public EntityClass read(Long id) {
		if (id == null) {
			return null;
		}
		// Формируем фильтр
		Map<String, String> filter = new HashMap<>();
		filter.put("id", id.toString()); // Предполагаем, что ключевой столбец один
		// Выполняем запрос в базу данных
		List<EntityClass> list = list(filter);
		if (list.size() > 1) {
			throw new IllegalArgumentException(ResourceMessages.getMessage("error.too.many.result"));
		}
		return list.isEmpty() ? null : list.get(0);
	}

	/**
	 * Выполняет sql-запрос с параметрами
	 *
	 * @param sql    текст sql-запроса
	 * @param params значения параметров запроса
	 * @return список полученный из БД сущностей
	 */
	protected abstract List<EntityClass> executeQuery(String sql, SqlParameterSource params);

	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		FilterUtils.createFilter(sql, params, filter, entityClass);
	}
	/**
	 * Обрабатывает условия постраничного отображения данных, если они были указаны
	 *
	 * @param filter исходный фильтр, в котором выполняется поиск параметров пейджинга
	 * @param sql    базовый запрос на получение данных, который будет оборачиваться фильтрацией по страницам
	 * @param params в данный аргмент будут записаны значения ограничений текущей страницы
	 */
	protected void buildPaging(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		PagingRange paging = PagingRange.fromFilter(filter);
		if (paging != null) {
			params.addValue("paging_from", paging.getFrom());
			params.addValue("paging_to", paging.getTo());

			StringBuilder sb = new StringBuilder();
			sql.insert(0, "WITH paging AS (\n");
			sql.append("\n)\nSELECT * FROM paging");
			sql.append(" WHERE nnn >= :paging_from AND nnn <= :paging_to");
		}
	}

	/**
	 * Формирует условия сортировки данных. Если они не указаны, но заданы параметры пейджинга,
	 * то сортировка ведется по ключевым полям.
	 *
	 * @param filter    входные параметры от клиента
	 * @param sql       формируемый sql-запрос
	 * @param insertPos позиция, куда необходимо вставить сформированные условия сортировки в sql-запрос
	 */
	protected void buildOrderBy(Map<String, String> filter, StringBuilder sql, int insertPos) {
		SortingInfo sort = SortingInfo.fromFilter(filter);
		List<String> sortColumns = null;
		if (sort != null) {
			sortColumns = getSortableColumnNames(sort); // сортируем записи по явно указанным столбцам таблицы
		} else if (filter.containsKey(PagingRange.PAGING_PARAM_NAME)) {
			sortColumns = getKeyColumnNames(); // сортируем по ключевым столбцам
		}
		if (sortColumns != null && !sortColumns.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			if (dbUtils.isTest()) {
				sb.append(" ROWNUM() nnn,\n");
				sql.append(" ORDER BY ");
				sql.append(StringUtils.join(sortColumns, ','));
			} else {
				sb.append(" ROW_NUMBER() OVER (ORDER BY ");
				sb.append(StringUtils.join(sortColumns, ','));
				sb.append(") nnn,\n");
			}
			sql.insert(insertPos, sb.toString());
		}
	}

	/**
	 * Возвращает список сортируемых столбцов в sql-виде: "column1 DESC, column2, column3 DESC, ...\n"
	 */
	protected List<String> getSortableColumnNames(@NotNull SortingInfo sort) {
		List<String> result = new ArrayList<>();
		Map<String, FieldMetaData> meta = MetaUtils.getFieldsMetaData(entityClass);
		for (String fieldName : sort.getColumns().keySet()) { // по всем столбцам, которые пользователь хочет отсортировать
			FieldMetaData fieldMeta = meta.get(fieldName);
			if (fieldMeta != null && fieldMeta.getColumnName() != null) { // обнаружили, что для этого столбца указано название столбца в бд
				StringBuilder sb = new StringBuilder();
				if (StringUtils.isNotBlank(fieldMeta.getTableAlias())) {
					sb.append(fieldMeta.getTableAlias()); // добавляем алиас таблицы
					sb.append('.');
				}
				sb.append(fieldMeta.getColumnName());
				if (!sort.getColumns().get(fieldName)) { // указываем направление сортировки
					sb.append(" DESC");
				}
				result.add(sb.toString());
			}
		}
		return result;
	}

	/**
	 * Возвращает список ключевых столбцов в sql-виде: "id DESC, code, ...\n"
	 */
	protected List<String> getKeyColumnNames() {
		List<String> result = new ArrayList<>();
		Map<String, FieldMetaData> meta = MetaUtils.getKeyProperties(entityClass);
		for (FieldMetaData fieldMeta : meta.values()) {
			if (fieldMeta != null && fieldMeta.getColumnName() != null) { // обнаружили, что для этого столбца указано название столбца в бд
				if (StringUtils.isNotBlank(fieldMeta.getTableAlias())) {
					result.add(fieldMeta.getTableAlias()); // добавляем алиас таблицы
				}
				result.add(fieldMeta.getColumnName());
			}
		}
		if (result.isEmpty()) {
			throw new IllegalArgumentException("У сущности должен быть указан хотя бы один ключевой столбец");
		}
		return result;
	}

}
