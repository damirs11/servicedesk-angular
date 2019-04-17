package ru.it.sd.model;

import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

/**
 * Источник
 * @author nsychev
 */
public class Source extends BaseCode {

	private static final long serialVersionUID = 5375161373505923051L;

	public Source(){}

	public static Long getTypeId(EntityType entityType) {

		switch(entityType){
			case SERVICECALL:
				return 667353114L;
			default:
				throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
		}
	}
}