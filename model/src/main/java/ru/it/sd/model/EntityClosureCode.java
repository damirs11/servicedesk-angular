package ru.it.sd.model;

import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

/**
 * Код завершения сущностей
 *
 * @author NSychev
 * @since 22.12.2017
 */
public class EntityClosureCode extends BaseCode {

	private static final long serialVersionUID = 5774642512114754719L;

	public EntityClosureCode(){}

	public static Long getTypeId(EntityType entityType) {
		switch (entityType) {
			case CHANGE:
				return 166000L;
			case SERVICECALL:
				return 75240L;
            case PROBLEM:
                return 75232L;
            case WORKORDER:
                return 75229L;
			default:
				throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
		}
	}
}