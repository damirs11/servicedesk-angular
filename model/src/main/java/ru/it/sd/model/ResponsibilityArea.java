package ru.it.sd.model;

/**
 * Зона ответственности
 *
 * @author NSychev
 * @since 16.05.2019
 */
public class ResponsibilityArea extends BaseCode {

	private static final long serialVersionUID = -1829729559230647182L;

	public ResponsibilityArea(){}

	public static Long getTypeId(EntityType entityType) {
		if (entityType == EntityType.SERVICECALL) {
			return 1079902224L;
		}
		throw new UnsupportedOperationException();
	}
}