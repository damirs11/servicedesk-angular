package ru.it.sd.model;

/**
 * Интерфейс для сущностей с назначением(исполнителем и группой исполнителей)
 *
 * @author quadrix
 * @since 02.08.2016
 */

public interface HasAssignment {

	Person getExecutor();

	void setExecutor(Person executor);

	Workgroup getWorkgroup();

	void setWorkgroup(Workgroup workgroup);

}