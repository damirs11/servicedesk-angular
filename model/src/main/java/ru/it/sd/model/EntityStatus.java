package ru.it.sd.model;

import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

/**
 * Статусы сущностей
 *
 * @author quadrix
 * @since 01.05.2017
 */
public class EntityStatus extends AbstractRepCode {

	@Override
	protected Long getTypeId(EntityType entityType) {
		switch (entityType) {
			case CHANGE:
				return 724041777L;
			case CALL:
				return 606535725L;
			case WORKORDER:
				return 854589446L;
			case ITEM:
				return 801767480L;
			case WORKGROUP:
				return 670761017L;
			case APPROVAL:
				return 281478244794379L; //itsm
			default:
				throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
		}
	}
}