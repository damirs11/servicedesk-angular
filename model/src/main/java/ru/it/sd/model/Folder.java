package ru.it.sd.model;

/**
 * Приоритет сущностей
 *
 * @author NSychev
 * @since 22.12.2017
 */
public class Folder extends BaseCode {

	public Folder(){}

	public static Long getTypeId() {
	    //Для всех сущностей одинаковый subtype
		return 396748L;
	}
}