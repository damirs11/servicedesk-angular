package ru.it.sd.service.holder;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.it.sd.service.CrudService;
import ru.it.sd.service.HasTemplateService;
import ru.it.sd.service.ReadService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Содержит ссылки на сервисы получения сущности по шаблону
 *
 * @author nsychev
 */
@Service
public class HasTemplateServiceHolder extends AbstractHolder<HasTemplateService> {

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Заполняем репозиторий бинами CrudService
	 */
	@Override
	public void initRepository() {
		Map<String, HasTemplateService> beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, HasTemplateService.class);
		for (HasTemplateService service : beans.values()) {
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
		Type type = entity.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType t = (ParameterizedType) type;
			if (t.getRawType() == ReadService.class ||
					t.getRawType() == CrudService.class) {
				return (Class) t.getActualTypeArguments()[0];
			}
		}
		return null;
	}
}