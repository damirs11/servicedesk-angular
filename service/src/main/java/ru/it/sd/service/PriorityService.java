package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.model.EntityPriority;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Сервис приоритетов
 *
 * @author quadrix
 * @since 26.07.2017
 */
@Service
public class PriorityService extends ReadService<EntityPriority> {

	private static final Logger LOG = LoggerFactory.getLogger(PriorityService.class);

	@Override
	public EntityPriority read(long id) {
		return EntityPriority.get(id);
	}

	@Override
	public List<EntityPriority> list(Map<String, String> filter) {
		return Arrays.asList(EntityPriority.values());
	}

	@Override
	public int count(Map<String, String> filter) {
		Map<String, String> clearFilter = AbstractEntityDao.clearFilter(filter);
		return list(clearFilter).size();
	}
}
