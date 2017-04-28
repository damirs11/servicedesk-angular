package com.aplana.sd.model;

/**
 * Организации, к которым соотносим пользователей системы
 *
 * @author quadrix
 *         28.04.2017 2:48
 */
public class AppOrganization {
	/** Идентификатор */
	private long id;
	/** Название */
	private String name;
	/** Email */
	private String email;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}