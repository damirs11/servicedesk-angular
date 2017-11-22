package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * Описание базовых параметров файла
 *
 * @author quadrix
 * @since 24.10.2017
 */
@ClassMeta(tableName = "rep_attachments", tableAlias = "ahs")
public class FileInfo implements HasId, Serializable {

	private static final long serialVersionUID = -8918064963338496454L;

	/** id вложения*/
	@FieldMeta(columnName = "ahs_oid")
	private Long id;
    /** id сущности*/
    @FieldMeta(columnName = "ahs_att_oid")
    private Long entityId;
    /** Тип сущности*/
    @FieldMeta(columnName = "ahs_ent_oid")
    private EntityType entityType;
    /** Путь к файлу на веб сервере*/
    @FieldMeta(columnName = "ahs_filename")
	private String path;

    /**
     * Внутренний идентификатор файла, по нему можно вычислить путь в файловой
     * системе до временного файла
     */
	private String fileId;
	/** Название файла */
	private String name;
	/** Размер файла в байтах */
	private long size;
	/** Пользователь, создавший файл */
	private Person author;
	/** Дата создания файла */
	private Date creationDate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
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

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
