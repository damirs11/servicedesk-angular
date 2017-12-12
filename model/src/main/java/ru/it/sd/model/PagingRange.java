package ru.it.sd.model;

import java.util.Map;
import java.util.StringTokenizer;

/**
 * Диапазон, используется при работе с табличными данными для реализации постраничного просмотра.
 *
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a>
 * @since 31.05.2016 17:48
 */

public final class PagingRange {

	public static final String PAGING_PARAM_NAME = "paging";
	public static final String PAGING_PARAM_SEPARATOR = ";";

	/** Начало диапазона */
	private int from = 0;
	/** Конец диапазона */
	private int to = Integer.MAX_VALUE;

	public PagingRange() {
		super();
	}

	public PagingRange(int from, int to) {
		this.from = from;
		this.to = to;
	}

	/**
	 * <p>Создание диапазона на основе данных параметров фильтрации. Если среди параметров есть параметр с
	 * именем 'paging', то диапазон формируется со значениями, указанными
	 * в данном параметре, иначе диапазон инициализируется значениями по умолчанию.</p>
	 *
	 * <p>Значение параметра представляет собой строку вида "10;20", где 10 - начало диапазона, а
	 * 20 - конец диапазона.</p>
	 * @param filterParams мапа из строковых параметров, в которых может присутствовать параметр пейджинга. Может быть NULL
	 * @return диапазон, может быть NULL, если не найден соответствующий параметр
	 * @throws IllegalArgumentException если значение параметра содержит более двух строковых элементов, разделенных ';'
	 * или они не могут быть преобразованы в числа
	 */
	public static PagingRange fromFilter(Map<String, String> filterParams) {
	 	if (isNeedPaging(filterParams)) {
			PagingRange range = new PagingRange();
			StringTokenizer values = new StringTokenizer(filterParams.get(PAGING_PARAM_NAME), PAGING_PARAM_SEPARATOR);
			// Должно быть минимум два параметра
			if (values.countTokens() != 2) {
				throw new IllegalArgumentException("Paging param count must be 2: \"from\" & \"to\"");
			}
			// Значения параметров должны быть числами
			try {
				range.setFrom(Integer.valueOf(values.nextToken()));
				range.setTo(Integer.valueOf(values.nextToken()));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Paging param must be the integer type", e);
			}
			return range;
		}
		return null;
	}

	public int getTo() {
		return to;
	}

	/**
	 * Выставляет количество элементов в диапазоне
	 * @param to количество, должно иметь значение больше 1
	 */
	public void setTo(int to) {
		if (to < 1) {
			throw new IllegalArgumentException("Argument \"to\" must have positive value. Current is " + to);
		}
		if (from > to) {
			throw new IllegalArgumentException("The values does not match: \"from\" > \"to\"");
		}
		this.to = to;
	}

	public int getFrom() {
		return from;
	}

	/**
	 * Выставляет начальное значение диапазона, его левую границу
	 * @param from должно иметь значение выше ноля
	 */
	public void setFrom(int from) {
		if (from < 1) {
			throw new IllegalArgumentException("Argument \"from\" must have positive value. Current is " + from);
		}
		if (from > to) {
			throw new IllegalArgumentException("The values does not match: \"from\" > \"to\"");
		}
		this.from = from;
	}

	public static boolean isNeedPaging(Map<String, String> filterParams) {
		return filterParams != null && filterParams.containsKey(PAGING_PARAM_NAME);
	}

	@Override
	public String toString() {
		return from + PAGING_PARAM_SEPARATOR + to;
	}
}