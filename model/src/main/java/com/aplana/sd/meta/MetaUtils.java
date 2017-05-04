package com.aplana.sd.meta;

import com.aplana.sd.exception.AppException;

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
	private static Map<Class<?>, List<FieldMetaData>> metaCache = new ConcurrentHashMap<>();

	/**
	 * Удаляет метаданные всех классов из кэша метаданных. Нужно, например, при обновлении настроек
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
	public static List<FieldMetaData> getFieldsMetaData(Class<?> beanClass) {
		if (!metaCache.containsKey(beanClass)) {
			PropertyDescriptor[] pds = extractPropertyDescriptors(beanClass);
			List<FieldMetaData> list = new ArrayList<>(pds.length);
			for (PropertyDescriptor pd : pds) {
				if (!STOP_PROPERTIES.contains(pd.getName())) {
					FieldMetaData propertyMetaData = createPropertyMetaData(beanClass, pd);
					list.add(propertyMetaData);
				}
			}
			metaCache.put(beanClass, sortAndFix(list));
		}

		return cloneMetaDataList(beanClass);
	}

	/**
	 * Метод для создания глубокой копии списка метаданных объекта. Нужен, чтобы избежать ненужных зависимостей кэша меданных от работы сервисов.
	 * @param beanClass класс сущности, для которого получаем метаданные
	 * @return глубокая копия списка полученных метаданных, если вдруг в кэше метаданных нет исключение
	 */
	private static List<FieldMetaData> cloneMetaDataList(Class<?> beanClass) {
		List<FieldMetaData> metaData = new ArrayList<>();
		if(metaCache.containsKey(beanClass)) {
			for (FieldMetaData propertyMetaData : metaCache.get(beanClass)) {
				metaData.add((FieldMetaData) propertyMetaData.clone());
			}
		}else {
			throw new AppException("Metadata for class " + beanClass.getName() + " not found");
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
	 * Сортирует список и делает его немодифицируемым.
	 *
	 * @param list исходный список
	 * @return сортированный немодифицируемый список
	 */
	private static List<FieldMetaData> sortAndFix(List<FieldMetaData> list) {
		Collections.sort(list);
		return Collections.unmodifiableList(list);
	}

	/**
	 * Получает метаданные свойства по его дескриптору
	 *
	 * @param beanClass          класс бина, у которого мы берем свойство и получаем метаданные
	 * @param propertyDescriptor дескриптор свойства
	 * @return метаданные свойства
	 */
	private static FieldMetaData createPropertyMetaData(
			Class<?> beanClass, PropertyDescriptor propertyDescriptor
	) {
		FieldMetaData pmd = new FieldMetaData();

		pmd.setName(propertyDescriptor.getName());
		pmd.setType(propertyDescriptor.getPropertyType());

		FieldMeta meta = AnnotationUtil.find(beanClass, propertyDescriptor, FieldMeta.class);
		if (meta != null) {
			pmd.setTitle(meta.title());
			pmd.setWidth(meta.width());
			pmd.setOrder(meta.order());
			pmd.setVisible(meta.visible());
			pmd.setFormat(meta.format());
			pmd.setPattern(meta.pattern());
			pmd.setPrecision(meta.precision());
			pmd.setMaxLength(meta.maxLength());
			pmd.setMinLength(meta.minLength());
			pmd.setMax(meta.max());
			pmd.setMin(meta.min());
			pmd.setRequired(meta.required());
			pmd.setReadOnly(meta.readOnly());
			pmd.setUnique(meta.unique());
		}

		return pmd;
	}

}