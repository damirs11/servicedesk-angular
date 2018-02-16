package ru.it.sd.model;

/**
 * Интерфейс для сущностей с папками
 *
 * @author quadrix
 * @since 02.08.2016
 */

public interface HasFolder{

	Folder getFolder();

	void setFolder(Folder folder);
}