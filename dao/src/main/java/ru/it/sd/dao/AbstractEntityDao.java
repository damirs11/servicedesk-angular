package ru.it.sd.dao;

import ru.it.sd.model.PagingRange;
import ru.it.sd.model.SortingInfo;

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
}
