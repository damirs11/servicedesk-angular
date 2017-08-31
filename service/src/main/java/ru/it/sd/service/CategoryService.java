package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.model.EntityCategory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Сервис категорий
 *
 * @author NSychev
 * @since 31.08.2017
 */
@Service
public class CategoryService implements ReadService<EntityCategory> {

	private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

	@Override
	public EntityCategory read(long id) {
		return EntityCategory.getById(id);
	}

	@Override
	public List<EntityCategory> list(Map<String, String> filter) {
		return Arrays.asList(EntityCategory.values());
	}

	@Override
	public int count(Map<String, String> filter) {
		Map<String, String> clearFilter = AbstractEntityDao.clearFilter(filter);
		return list(clearFilter).size();
	}
}
