package ru.it.sd.model;

import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

/**
 * Система
 * (Кастомное поле) 1
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
			case CONFIGURATIONITEM:
				return 1079902257L;
			//Incident 1065680941
			//Maintenance?? 1080164392
			case ORGANIZATION:
				return 1080164377L;
			case PERSON:
				return 1080164362L;
			case PROBLEM:
				return 1079902227L;
			case SERVICECALL:
				return 1079902209L;
			case WORKORDER:
				return 1079902242L;
			case WORKGROUP:
				return 281478354436219L;
			default:
				throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
		}
	}
}