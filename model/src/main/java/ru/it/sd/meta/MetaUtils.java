package ru.it.sd.meta;

import ru.it.sd.exception.AppException;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author quadrix
 * @since 03.05.2017
 */
public class MetaUtils {

	/**
	 * Это нужно так-как любой объект имеет свойство class, его надо исключать.
	 * Неисключено, что мы ещё какие-то свойства добавим, которые не нужно добавлять в метаданные.
	 */
	private static final Collection<String> STOP_PROPERTIES;

	static {
		Set<String> set = new HashSet<>();
		set.add("class");
		STOP_PROPERTIES = Collections.unmodifiableCollection(set);
	}

	/**
	 * Кэш метаданных с синхронизируемой записью
	 */
	private static Map<Class, Map<String, FieldMetaData>> metaCache = new ConcurrentHashMap<>();

	/**
	 * Удаляет метаданные всех классов из кэша метаданных.
	 */
	public static void resetCache() {
		metaCache.clear();
	}

	/**
	 * Возвращает метаданные всех свойств класса, в том числе невидимых и нередактируемых.
	 *
	 * @param beanClass класс бина, метаданные которого нужно получить.
	 * @return список метаданных свойств бина в порядке, заданном полем order
	 */
	public static Map<String, FieldMetaData> getFieldsMetaData(Class beanClass) {
		if (!metaCache.containsKey(beanClass)) {
			PropertyDescriptor[] pds = extractPropertyDescriptors(beanClass);
			Map<String, FieldMetaData> data = new HashMap<>(pds.length);
			for (PropertyDescriptor pd : pds) {
				if (!STOP_PROPERTIES.contains(pd.getName())) {
					data.put(pd.getName(), createPropertyMetaData(beanClass, pd));
				}
			}
			metaCache.put(beanClass, data);
		}
		return cloneMetaDataList(beanClass);
	}

	/**
	 * Метод для создания глубокой копии списка метаданных объекта. Нужен, чтобы избежать ненужных зависимостей кэша меданных от работы сервисов.
	 * @param beanClass класс сущности, для которого получаем метаданные
	 * @return глубокая копия списка полученных метаданных, если вдруг в кэше метаданных нет исключение
	 */
	private static Map<String, FieldMetaData> cloneMetaDataList(Class<?> beanClass) {
		Map<String, FieldMetaData> metaData = new HashMap<>();
		if(metaCache.containsKey(beanClass)) {
			for (FieldMetaData propertyMetaData : metaCache.get(beanClass).values()) {
				metaData.put(propertyMetaData.getName(), (FieldMetaData) propertyMetaData.clone());
			}
		}else {
			throw new AppException("Metadata for class {0} not found", beanClass.getName());
		}
		return metaData;
	}

	/**
	 * @param beanClass класс, чьи свойства нужно получить
	 * @return массив дескрипторов свойств класса
	 */
	private static PropertyDescriptor[] extractPropertyDescriptors(Class<?> beanClass) {
		try {
			return Introspector.getBeanInfo(beanClass).getPropertyDescriptors();
		} catch (IntrospectionException e) {
			throw new AppException(
					MessageFormat.format("Can''t read properties of class {0}.", beanClass.getSimpleName()), e
			);
		}
	}

	/**
	 * Получает метаданные свойства по его дескриптору
	 *
	 * @param beanClass          класс бина, у которого мы берем свойство и получаем метаданные
	 * @param propertyDescriptor дескриптор свойства
	 * @return метаданные свойства
	 */
	private static FieldMetaData createPropertyMetaData(Class beanClass, PropertyDescriptor propertyDescriptor) {
		FieldMetaData md = new FieldMetaData();
		md.setAnnotation(false);
		md.setName(propertyDescriptor.getName());
		md.setType(propertyDescriptor.getPropertyType());
		FieldMeta meta = AnnotationUtil.find(beanClass, propertyDescriptor, FieldMeta.class);
		if (meta != null) {
			md.setAnnotation(true);
			md.setColumnName(meta.columnName());
			md.setTableAlias(meta.tableAlias());
			md.setMaxLength(meta.maxLength());
			md.setMinLength(meta.minLength());
			md.setMax(meta.max());
			md.setMin(meta.min());
			md.setRequired(meta.required());
			md.setReadOnly(meta.readOnly());
			md.setUnique(meta.unique());
			md.setPattern(meta.pattern());
			md.setKey(meta.key());
			md.setAttribute(meta.attribute());
		}
		return md;
	}

	/**
	 * Возвращает список уникальных полей (первичный ключ) класса
	 */
	public static Map<String, FieldMetaData> getKeyProperties(Class clazz){
		Map<String, FieldMetaData> result = new HashMap<>();
		for (FieldMetaData meta : getFieldsMetaData(clazz).values()) {
			if (meta.isKey()) {
				result.put(meta.getName(), meta);
			}
		}
		return result;
	}

}