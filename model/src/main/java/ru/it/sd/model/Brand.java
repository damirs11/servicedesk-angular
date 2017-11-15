package ru.it.sd.model;

/**
 * Торговая марка
 *
 * Created by MYXOMOPX on 013 13.06.17.
 */
public class Brand extends BaseCode {

	@Override
	protected Long getTypeId(EntityType entityType) {
		return null;
	}

	public static Brand from(BaseCode code) {
		if (code == null) {
			return null;
		}
		Brand result = new Brand();
		result.setId(code.getId());
		result.setName(code.getName());
		return result;
	}
}