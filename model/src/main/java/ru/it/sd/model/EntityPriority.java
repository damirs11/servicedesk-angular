package ru.it.sd.model;

/**
 * Приоритет сущностей
 *
 * @author NSychev
 * @since 22.12.2017
 */
public class EntityPriority extends BaseCode {

	public EntityPriority(){}

	public static Long getTypeId() {
	    //Для всех сущностей одинаковый subtype
		return 889389215L;
	}
}