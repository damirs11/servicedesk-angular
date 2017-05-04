package com.aplana.sd.service.security;

import org.springframework.security.access.PermissionEvaluator;

/**
 * Интерфейс предназначен для реализации специальной логики проверки прав доступа
 *
 * @author quadrix
 * @since 04.08.2016
 */
public interface CustomPermissionEvaluator extends PermissionEvaluator {

	/**
	 * Возвращает идентификатор экземпляра данного класса. Необходим для поиска конкретного экземпляра
	 * среди множества подобных.
	 *
	 * @return
	 */
	String getKey();
}