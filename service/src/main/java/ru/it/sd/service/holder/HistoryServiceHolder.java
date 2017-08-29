package ru.it.sd.service.holder;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.it.sd.service.History;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Содержит ссылки на сервисы для чтения истории сущностей.
 *
 * @author mfayzullin
 */
@Service
public class HistoryServiceHolder extends AbstractHolder<History> {

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Заполняем репозиторий
	 */
	@Override
	public void initRepository() {
		Map<String, History> beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, History.class);
		for (History service : beans.values()) {
			Class entityClass = getEntityClass(service);
			if (entityClass != null) {
				storeValue(entityClass.getSimpleName(), service);
			}
		}
	}

	/**
	 * Возвращает класс, который указан в интерфейсе указанного сервиса
	 *
	 * @param entity исследуемый сервис
	 * @return модельный класс сервиса
	 */
	private Class getEntityClass(Object entity) {
		for (Type type : entity.getClass().getGenericInterfaces()) {
			if (type instanceof ParameterizedType) {
				ParameterizedType t = (ParameterizedType) type;
				if (t.getRawType() == History.class) {
					return (Class) t.getActualTypeArguments()[0];
				}
			}
		}
		return null;
	}
}