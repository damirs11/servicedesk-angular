package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.model.EntityClassification;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Сервис классификаций
 *
 * @author NSychev
 * @since 31.08.2017
 */
@Service
public class ClassificationService implements ReadService<EntityClassification> {

	private static final Logger LOG = LoggerFactory.getLogger(ClassificationService.class);

	@Override
	public EntityClassification read(long id) {
		return EntityClassification.getById(id);
	}

	@Override
	public List<EntityClassification> list(Map<String, String> filter) {
		return Arrays.asList(EntityClassification.values());
	}

	@Override
	public int count(Map<String, String> filter) {
		Map<String, String> clearFilter = AbstractEntityDao.clearFilter(filter);
		return list(clearFilter).size();
	}
}
