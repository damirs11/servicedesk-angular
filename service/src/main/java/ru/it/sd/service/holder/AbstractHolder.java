package ru.it.sd.service.holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Абстрактный класс для репозиториев объектов, связанных с классами сущностей. В частности, используется для поиска
 * реализаций сервисов соответствующих типов сущностей.
 *
 * @author quadrix
 * @since 22.05.2017
 */
public abstract class AbstractHolder<StoreClass> implements DisposableBean {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractHolder.class);

	/** Хранилище всех зарегистрированных объектов */
    private final Map<String, StoreClass> repository = new HashMap<>();

    /**
     * Возвращает хранимый объект по идентификатору
     *
     * @param key идентификатор объекта поиска
     * @return
     */
    public StoreClass findFor(String key) {
		StoreClass searchResult = repository.get(key);
        if (searchResult != null) {
            return searchResult;
        } else {
            throw new IllegalStateException(
					MessageFormat.format("Repository doesn't contain value for a key \"{0}\".", key)
            );
        }
    }

	@Override
	public void destroy() throws Exception {
		repository.clear();
	}

	@PostConstruct
	private void init() {
		repository.clear();
		initRepository();
	}

	/**
	 * Процедура инициализации репозитория
	 */
	protected abstract void initRepository();

	private Object lock = new Object(); // асинхронная блокировка операции

	/**
	 * Запись значения в репозиторий
	 *
	 * @param key
	 * @param storeClass
	 */
	protected void storeValue(String key, StoreClass storeClass) {
		synchronized (lock) { // на тот случай, если между проверкой наличия объекта в репозитории и его записью проскочит другой поток
			if (repository.containsKey(key)) {
				throw new IllegalStateException(
						MessageFormat.format("Repository already contains value for a key \"{0}\".", key)
				);
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug(MessageFormat.format("Store \"{0}\" for key \"{1}\"",
						storeClass.getClass().getSimpleName(),
						key));
			}
			repository.put(key, storeClass);
		}
	}

	protected void storeValue(Class clazz, StoreClass storeClass) {
		storeValue(clazz.getName(), storeClass);
	}

	protected void storeValues(Map<Class, StoreClass> values) {
		for (Map.Entry<Class, StoreClass> entry : values.entrySet()) {
			storeValue(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Простое чтение из репозитория
	 *
	 * @param key идентификатор объекта для поиска в репозитории
	 * @return
	 */
	protected StoreClass readValue(String key) {
		return repository.get(key);
	}
}