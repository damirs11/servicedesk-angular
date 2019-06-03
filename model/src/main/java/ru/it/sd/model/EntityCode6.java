package ru.it.sd.model;

import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

/**
 * Система
 * (Кастомное поле) 7
 * @author NSychev
 * @since 22.12.2017
 */
public class EntityCode6 extends BaseCode {

	private static final long serialVersionUID = 4889770089180475611L;

	public EntityCode6(){}

	public static Long getTypeId(EntityType entityType) {

		switch(entityType){
			case SERVICECALL:
				return 281481741664798L;
			//case INCIDENT: return 281484034900706
			default:
				throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
		}
	}
}