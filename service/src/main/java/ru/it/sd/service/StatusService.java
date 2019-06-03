package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.EntityType;

/**
 * Сервис статусов
 *
 * @author quadrix
 * @since 26.07.2017
 */
@Service
public class StatusService extends AbstractCodeService<EntityStatus> {
	@Override
	protected Long getTypeId(EntityType entityType) {
		return EntityStatus.getTypeId(entityType);
	}
}
