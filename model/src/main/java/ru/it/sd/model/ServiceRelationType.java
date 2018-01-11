package ru.it.sd.model;

/**
 * Тип взаимосвязи
 *
 * @author NSychev
 * @since 25.12.2017
 */
public class ServiceRelationType extends BaseCode {

	public ServiceRelationType(){}

	public static Long getTypeId() {
	    //Для всех сущностей одинаковый subtype
		return 77401L;
	}
}