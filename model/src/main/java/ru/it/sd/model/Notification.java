package ru.it.sd.model;

/**
 * Уведомление
 *
 * @author NSychev
 * @since 16.05.2019
 */
public class Notification extends BaseCode {

	private static final long serialVersionUID = -1829729559230647182L;

	public Notification(){}

	public static Long getTypeId(EntityType entityType) {
		if (entityType == EntityType.SERVICECALL) {
			return 1079902221L;
		}
		throw new UnsupportedOperationException();
	}
}