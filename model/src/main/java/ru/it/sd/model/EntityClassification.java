package ru.it.sd.model;

import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

/**
 * Классификация сущностей
 *
 * @author NSychev
 * @since 22.12.2017
 */
public class EntityClassification extends BaseCode {

	public EntityClassification(){}

	public static Long getTypeId(EntityType entityType) {
		switch (entityType) {
			case CHANGE:
				return 165879L;
			case CALL:
				return 1103560728L;
            case PROBLEM:
                return 1103560717L;
			default:
				throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
		}
	}
}