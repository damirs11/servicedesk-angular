package ru.it.sd.model;

import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

/**
 * Система
 * (Кастомное поле) 7
 * @author NSychev
 * @since 22.12.2017
 */
public class EntityCode7 extends BaseCode {

	private static final long serialVersionUID = 4889770089180475611L;

	public EntityCode7(){}

	public static Long getTypeId(EntityType entityType) {

		switch(entityType){
			case CONFIGURATIONITEM:
				return 281481741664789L;
			case WORKORDER:
				return 281481741664794L;
			case SERVICECALL:
				return 281481741664799L;
			case CHANGE:
				return 281484034900702L;
			case PERSON:
				return 281484034900711L;
			//case INCIDENT: return 281484034900706
			default:
				throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
		}
	}
}