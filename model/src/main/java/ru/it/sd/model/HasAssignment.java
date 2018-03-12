package ru.it.sd.model;

/**
 * Интерфейс для сущностей с назначением(исполнителем и группой исполнителей)
 *
 * @author quadrix
 * @since 02.08.2016
 */

public interface HasAssignment {

	Assignment getAssignment();

	void setAssignment(Assignment assignment);

}