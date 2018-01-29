package ru.it.sd.model;

/**
 * Интерфейс для объектов с идентификаторами
 *
 * @author quadrix
 * @since 02.08.2016
 */

public interface Entity extends HasId,HasStatus{

	Person getExecutor();

	void setExecutor(Person executor);

	Workgroup getWorkgroup();

	void setWorkgroup(Workgroup workgroup);

	Folder getFolder();

	void setFolder(Folder folder);
}