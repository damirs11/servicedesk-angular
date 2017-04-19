package com.aplana.sd.service.security;

import org.springframework.security.access.PermissionEvaluator;

/**
 * Интерфейс предназначен для реализации специальной логики проверки прав доступа
 *
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a>
 * @since 04.08.2016 11:52
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