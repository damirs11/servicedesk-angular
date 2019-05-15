package ru.it.sd.model;

/**
 * Функционал
 *
 * @author NSychev
 * @since 16.05.2019
 */
public class Functional extends BaseCode {

	private static final long serialVersionUID = -1829729559230647182L;

	public Functional(){}

	public static Long getTypeId(EntityType entityType) {
	    switch (entityType) {
			case SERVICECALL: return 1079902215L;
			case PROBLEM: return 1079902227L;
		}
		throw new UnsupportedOperationException();
	}
}