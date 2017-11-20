package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.EntityType;
import ru.it.sd.util.ResourceMessages;

import java.util.ArrayList;
import java.util.HashMap;
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

	private final CodeDao codeDao;

	@Autowired
	public StatusService(CodeDao codeDao) {
		this.codeDao = codeDao;
	}

	@Override
	public EntityStatus read(long id) {
		BaseCode code = codeDao.read(id);
		return code == null ? null : code.convertTo(EntityStatus.class);
	}

	@Override
	public List<EntityStatus> list(Map<String, String> filter) {
		String entityType = filter.get("entityType");
		if (entityType == null) {
			throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
		}
		Long subType = EntityStatus.getTypeId(EntityType.get(Long.parseLong(entityType)));
		Map<String, String> subFilter = new HashMap<>();
		subFilter.put("subtype", subType.toString());
		List<BaseCode> codes = codeDao.list(subFilter);
		List<EntityStatus> result = new ArrayList<>();
		codes.forEach((code) ->
			result.add(code.convertTo(EntityStatus.class))
		);
		return result;
	}

	@Override
	public int count(Map<String, String> filter) {
		Map<String, String> clearFilter = AbstractEntityDao.clearFilter(filter);
		return list(clearFilter).size();
	}
}
