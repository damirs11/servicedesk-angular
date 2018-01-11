package ru.it.sd.model;

import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

/**
 * Статусы сущностей
 *
 * @author quadrix
 * @since 01.05.2017
 */
public class EntityStatus extends BaseCode {

	private static final long serialVersionUID = 2137071665754239831L;

	public EntityStatus(){}

	public static Long getTypeId(EntityType entityType) {
		switch (entityType) {
			case CHANGE:
				return 724041777L; // rep
			case CALL:
				return 606535725L; // rep
			case WORKORDER:
				return 854589446L; // rep
			case ITEM:
				return 801767480L; // rep
			case WORKGROUP:
				return 670761017L; // rep
			case APPROVAL:
				return 281478244794379L; //itsm
			default:
				throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
		}
	}
}