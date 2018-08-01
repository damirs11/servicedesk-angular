package ru.it.sd.model;

import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

/**
 * Система
 * (Кастомное поле)
 * @author NSychev
 * @since 22.12.2017
 */
public class EntityCode1 extends BaseCode {

	private static final long serialVersionUID = 4889770089180475611L;

	public EntityCode1(){}

	public static Long getTypeId(EntityType entityType) {

		switch(entityType){
			case CHANGE:
				return 165944L;
			case PROBLEM:
				return 1079902227L;
			default:
				throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
		}
	}
}