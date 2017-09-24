package ru.it.sd.model;

/**
 * Типы сущностей, с которыми работает система
 *
 * @author quadrix
 * @since 01.05.2017
 */
public enum EntityType implements Code {

	CHANGE("Изменение", 724041768L),
	PROBLEM("Проблема"),
	CALL("Обращение"),
	WORKORDER("Наряд"),
	ITEM("Объект"),
	WORKGROUP("Рабочая группа");

	/** Название */
	private String title;
	/** Идентификатор */
	private Long id;

	EntityType(String title) {
		this.title = title;
	}

	EntityType(String title, Long id) {
		this(title);
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getName() {
		return title;
	}

	@Override
	public void setName(String name) {
		throw new UnsupportedOperationException();
	}
}