package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.model.EntityStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Сервис статусов
 *
 * @author quadrix
 * @since 26.07.2017
 */
@Service
public class StatusService implements ReadService<EntityStatus> {

	private static final Logger LOG = LoggerFactory.getLogger(StatusService.class);


	@Override
	public EntityStatus read(long id) {
		return EntityStatus.get(id);
	}

	@Override
	public List<EntityStatus> list(Map<String, String> filter) {
		List<EntityStatus> result = Arrays.asList(EntityStatus.values());
		if (filter != null) {
			String entityType = filter.get("entityType");
			if (entityType != null) {
				result.removeIf(s -> s.getEntityType() == null || !s.getEntityType().name().equals(entityType));
			}
		}
		return result;
	}

	@Override
	public int count(Map<String, String> filter) {
		Map<String, String> clearFilter = AbstractEntityDao.clearFilter(filter);
		return list(clearFilter).size();
	}
}
