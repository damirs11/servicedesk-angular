package ru.it.sd.model;

/**
 * Приоритет сущностей
 *
 * @author NSychev
 * @since 22.12.2017
 */
public class EntityPriority extends BaseCode {

	private static final long serialVersionUID = 5270563648746819342L;

	public EntityPriority(){}

	public static Long getTypeId(EntityType entityType) {
	    //Для всех сущностей одинаковый subtype

		switch(entityType){
			case ASSIGNMENT:
				return 76990L;
			default:
				return 889389215L;
		}
	}
}