package ru.it.sd.model;

/**
 * Папка доступа
 *
 * Created by MYXOMOPX on 013 13.06.17.
 */
public class Folder extends BaseCode {

	@Override
	protected Long getTypeId(EntityType entityType) {
		return null;
	}

	public static Folder from(BaseCode code) {
		if (code == null) {
			return null;
		}
		Folder result = new Folder();
		result.setId(code.getId());
		result.setName(code.getName());
		return result;
	}
}
