package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.util.AppToStringStyle;

import java.util.Date;

/**
 * Описание базовых параметров файла
 *
 * @author quadrix
 * @since 24.10.2017
 */
public class FileInfo {

	/**
	 * Внутренний идентификатор файла, по нему можно вычислить путь в файловой
	 * системе до временного файла
	 */
	private String id;
	/** Название файла */
	private String name;
	/** Размер файла в байтах */
	private long size;
	/** Пользователь, создавший файл */
	private Person author;
	/** Дата создания файла */
	private Date creationDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Person getAuthor() {
		return author;
	}

	public void setAuthor(Person author) {
		this.author = author;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, AppToStringStyle.getInstance())
				.append(name)
				.append(size)
				.append(author)
				.append(creationDate)
				.build();		                 		                 		                 		                 		                 	}
}
