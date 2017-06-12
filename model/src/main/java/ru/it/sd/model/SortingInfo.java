package ru.it.sd.model;

import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Содержит информацию о сортировке табличных данных
 *
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a>
 * @since 01.06.2016 16:41
 */

public final class SortingInfo {

	public static final String SORTING_PARAM_NAME = "sort";
	public static final char SORTING_PARAM_SEPARATOR = ';';
	public static final char SORTING_DIRECTION_SEPARATOR = '-';
	public static final String SORTING_DIRECTION_ASC = "asc";
	public static final String SORTING_DIRECTION_DESC = "desc";

	/**
	 * <p>Список сортируемых полей и направлений сортировки</p>
	 *
	 * <pre>
	 * List&lt;Entry&lt;String, Boolean&gt;&gt;
	 *
	 * String - название поля
	 * Boolean - направление сортировки (true - по возрастанию, false - по убыванию)
	 * </pre>
	 */
	private List<Map.Entry<String, Boolean>> columns = new ArrayList<>();

	public SortingInfo(){
		super();
	}

	/**
	 * <p>Извлекает информацию о сортировке на основе данных параметров фильтрации. Для этого среди параметров
	 * должен быть параметр с именем 'sort'.</p>
	 *
	 * <p>Пример:</p>
	 * <pre>map.put("sort", "name-asc;description-desc;sum-asc");</pre>
	 *
	 * <p>Для описания используется формат "&lt;название поля&gt; - &lt;направление сортировки (asc\desc)&gt;"</p>
	 *
	 * @param filterParams мапа из строковых параметров, в которых может присутствовать информация о сортировке
	 *                     столбцов. Может быть NULL
	 * @return если найдена информация, то возвращается объект, в противном случае NULL
	 */
	public static SortingInfo fromFilterParams(Map<String, String> filterParams) {
		if (filterParams != null && filterParams.containsKey(SORTING_PARAM_NAME)) {
			String sortingParam = filterParams.get(SORTING_PARAM_NAME).trim();
			if (org.apache.commons.lang3.StringUtils.isBlank(sortingParam)) {
				return null;
			}
			SortingInfo result = new SortingInfo();
			// Добавляем в конец строки символ ";", если его там нет. Нужно для правильного разбора строки
			if (SORTING_PARAM_SEPARATOR != sortingParam.charAt(sortingParam.length() - 1)) {
				sortingParam += SORTING_PARAM_SEPARATOR;
			}
			// Разбираем строку на пары значений
			String[] tokens = StringUtils.tokenizeToStringArray(sortingParam, "" + SORTING_DIRECTION_SEPARATOR + SORTING_PARAM_SEPARATOR);
			if (tokens.length % 2 != 0 ) {
				throw new IllegalArgumentException("It's not array of pairs");
			}
			String sortColumn;
			Boolean sortDirection;
			Set<String> columnNames = new HashSet<>(); // Для проверки дубликатов
			for (int i = 0; i < tokens.length / 2; i++) {
				sortColumn = tokens[i * 2];
				if (columnNames.contains(sortColumn)) {
					throw new IllegalArgumentException("Found duplicate columns");
				}
				columnNames.add(sortColumn);
				String token = tokens[i * 2 + 1].trim();
				if (!SORTING_DIRECTION_ASC.equals(token) && !SORTING_DIRECTION_DESC.equals(token)) {
					throw new IllegalArgumentException("Illegal sort direction value");
				}
				sortDirection = SORTING_DIRECTION_ASC.equals(token);
				result.columns.add(new AbstractMap.SimpleEntry<>(sortColumn, sortDirection));
			}
			return result.columns.isEmpty() ? null : result;
		}
		return null;
	}

	public List<Map.Entry<String, Boolean>> getColumns() {
		return columns;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Map.Entry<String, Boolean>> iterator = columns.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Boolean> column = iterator.next();
			sb.append(column.getKey())
					.append(SORTING_DIRECTION_SEPARATOR)
					.append(column.getValue() ? SORTING_DIRECTION_ASC : SORTING_DIRECTION_DESC);
			if (iterator.hasNext()) {
				sb.append(SORTING_PARAM_SEPARATOR);
			}
		}
		return sb.toString();
	}
}