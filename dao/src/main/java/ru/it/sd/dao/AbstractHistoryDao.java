package ru.it.sd.dao;

import org.apache.commons.collections.MapUtils;
import ru.it.sd.exception.BadRequestException;
import ru.it.sd.model.HasId;
import ru.it.sd.util.ResourceMessages;

import java.util.Map;

/**
 * Базовый класс для работы с историей сущностей
 */
public abstract class AbstractHistoryDao<EntityClass extends HasId> extends AbstractEntityDao<EntityClass> {

	/**
	 * Проверяем необходимые условия фильтрации для получения истории
	 * @param filter проверяемый фильтр
	 */
	protected void checkFilterRequirements(Map<String, String> filter) {
		// Фильтр должен быть задан
		if (MapUtils.isEmpty(filter)) {
			throw new BadRequestException(ResourceMessages.getMessage("error.dao.filter"));
		}
		// Проверяем фильтрацию для списка и конкретной записи истории
		if (!filter.containsKey("entityId") && !filter.containsKey("id")) {
			throw new BadRequestException(ResourceMessages.getMessage("error.dao.filter.key", "entityId\" или \"id"));
		}
	}

}