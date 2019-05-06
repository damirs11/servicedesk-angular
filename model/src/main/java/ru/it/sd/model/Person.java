package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Класс для хранения дополнительной информации о пользователе
 *
 * @author quadrix
 * @since 03.05.2017
 */
@ClassMeta(tableName = "itsm_persons", tableAlias = "p")
public class Person implements HasId, HasFolder, Serializable {

	private static final long serialVersionUID = 6269144031555905094L;
	private static final Logger LOG = LoggerFactory.getLogger(Person.class);

	public Person() {
	}

	public Person(Long id) {
		this.id = id;
	}

	/** Уникальный идентификатор */
	@FieldMeta(columnName = "per_oid", key = true)
	private Long id;
	/** Пол: true(1) - женский, false(0) - мужской*/
	@FieldMeta(columnName = "per_gender")
	private Boolean sex;
	/** Email */
	@FieldMeta(columnName = "per_email")
	private String email;
	/** Должность */
	@FieldMeta(columnName = "per_jobtitle")
	private String job;
	/** Имя */
	@FieldMeta(columnName = "per_firstname")
	private String firstName;
	/** Фамилия */
	@FieldMeta(columnName = "per_lastname")
	private String lastName;
	/** Отчество */
	@FieldMeta(columnName = "per_middlename")
	private String middleName;

	@FieldMeta(columnName = "per_name")
    private String fullname;
	/** Организация */
	@FieldMeta(columnName = "per_org_oid")
	private Organization organization;

	@FieldMeta(columnName = "per_poo_oid")
	private Folder folder;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Boolean isSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getShortName() {
		return (lastName != null ? lastName : "-") +
				(firstName != null && firstName.length() > 1 ? " " + firstName.charAt(0) + '.' : "") +
				(middleName != null && middleName.length() > 1 ? " " + middleName.charAt(0) + '.' : "");
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, AppToStringStyle.getInstance())
				.append("id", this.id)
				.append("sex", this.sex)
				.append("email", this.email)
				.append("job", this.job)
				.append("firstName", this.firstName)
				.append("lastName", this.lastName)
				.append("middleName", this.middleName)
				.append("organization", this.organization)
				.toString();
	}

	@Override
	public Folder getFolder() {
		return folder;
	}

	@Override
	public void setFolder(Folder folder) {
		this.folder = folder;
	}
}