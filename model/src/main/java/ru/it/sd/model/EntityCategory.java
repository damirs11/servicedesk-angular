package ru.it.sd.model;

import ru.it.sd.exception.ServiceException;
import ru.it.sd.util.ResourceMessages;

/**
 * Категории сущностей
 *
 * @author NSychev
 * @since 22.12.2017
 */
public class EntityCategory extends BaseCode {

	private static final long serialVersionUID = 2137071665754239831L;

	public EntityCategory(){}

	public static Long getTypeId(EntityType entityType) {
		switch (entityType) {
			case CHANGE:
				return 673513486L;
			case SERVICECALL:
				return 673513478L;
			case WORKORDER:
				return 878116890L;
			case CONFIGURATIONITEM:
                return 801767456L;
            case PROBLEM:
                return 717488183L;
			default:
				throw new ServiceException(ResourceMessages.getMessage("error.entity.type"));
		}
	}
}