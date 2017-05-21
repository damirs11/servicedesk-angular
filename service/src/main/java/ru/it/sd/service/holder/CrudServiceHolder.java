package ru.it.sd.service.holder;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.it.sd.service.CrudService;
import ru.it.sd.service.ReadService;
import ru.it.sd.util.EntityUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.Map;

/**
 * Содержит ссылки на сервисы для чтения сущностей.
 *
 * @author mfayzullin
 */
@Service
public class CrudServiceHolder extends AbstractHolder<CrudService> {

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Заполняем репозиторий бинами CrudService
	 */
	@Override
	public void initRepository() {
		Map<String, CrudService> beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, CrudService.class);
		for (CrudService service : beans.values()) {
			Class entityClass = getEntityClass(service);
			if (entityClass == null) {
				throw new IllegalStateException(
						MessageFormat.format("ReadService \"{0}\" doesn't retrive entity class type", service.getClass().getSimpleName()));
			}
			storeValue(entityClass.getSimpleName(), service);
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
				if (t.getRawType() == CrudService.class) {
					return (Class) t.getActualTypeArguments()[0];
				}
			}
		}
		return null;
	}
}