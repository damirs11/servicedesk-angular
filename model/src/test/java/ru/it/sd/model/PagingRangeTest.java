package ru.it.sd.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a>
 * @since 31.05.2016 18:22
 */

public class PagingRangeTest {

	@Test
	private void testPagingRange() {
		// Значения по умолчанию
		PagingRange range = new PagingRange();
		assertEquals(range.getFrom(), 0);
		assertEquals(range.getTo(), Integer.MAX_VALUE);

		range = new PagingRange(6, 78);
		assertEquals(range.getFrom(), 6);
		assertEquals(range.getTo(), 78);
	}

	@Test
	private void testFromFilterParams() {
		final Map<String, String> filterParams = new HashMap<>();
		filterParams.put("name", "Вася");
		// Основной тест
		filterParams.put(PagingRange.PAGING_PARAM_NAME, 9 + PagingRange.PAGING_PARAM_SEPARATOR + 10);
		PagingRange range = PagingRange.fromFilter(filterParams);
		assertEquals(range.getFrom(), 9);
		assertEquals(range.getTo(), 10);
		// Проверка пустых значений
		filterParams.remove(PagingRange.PAGING_PARAM_NAME);
		range = PagingRange.fromFilter(filterParams);
		assertNull(range);
		range = PagingRange.fromFilter(null);
		assertNull(range);
		// Более двух значений
		filterParams.put(PagingRange.PAGING_PARAM_NAME, 9 + PagingRange.PAGING_PARAM_SEPARATOR + 10 + PagingRange.PAGING_PARAM_SEPARATOR + 11);
		assertThrows(IllegalArgumentException.class, new Assert.ThrowingRunnable(){
			@Override
			public void run() {
				PagingRange.fromFilter(filterParams);
			}
		});
		// Вместо числа строка
		filterParams.put(PagingRange.PAGING_PARAM_NAME, 9 + PagingRange.PAGING_PARAM_SEPARATOR + "wrongValue");
		assertThrows(IllegalArgumentException.class, new Assert.ThrowingRunnable(){
			@Override
			public void run() {
				PagingRange.fromFilter(filterParams);
			}
		});
	}

	@Test
	private void testSetTo() {
		final PagingRange range = new PagingRange();
		// Основной тест
		range.setTo(9);
		assertEquals(range.getTo(), 9);
		range.setTo(1);
		assertEquals(range.getTo(), 1);
		// Тест неправильных значений
		assertThrows(IllegalArgumentException.class, new Assert.ThrowingRunnable(){
			@Override
			public void run() {
				range.setTo(0);
			}
		});
		final PagingRange range2 = new PagingRange(5, 7);
		assertThrows(IllegalArgumentException.class, new Assert.ThrowingRunnable(){
			@Override
			public void run() {
				range2.setTo(4);
			}
		});
	}

	@Test
	private void testSetFrom() {
		final PagingRange range = new PagingRange();
		// Основной тест
		range.setFrom(4);
		assertEquals(range.getFrom(), 4);
		range.setFrom(1);
		assertEquals(range.getFrom(), 1);
		// Тест неправильных значений
		assertThrows(IllegalArgumentException.class, new Assert.ThrowingRunnable(){
			@Override
			public void run() {
				range.setFrom(0);
			}
		});
		final PagingRange range2 = new PagingRange(5, 7);
		assertThrows(IllegalArgumentException.class, new Assert.ThrowingRunnable(){
			@Override
			public void run() {
				range2.setFrom(8);
			}
		});
	}

	@Test
	private void testToString() {
		PagingRange range = new PagingRange(5, 8);
		assertEquals(range.toString(), 5 + PagingRange.PAGING_PARAM_SEPARATOR + 8);
	}

}